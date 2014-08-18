package com.omnipaste.droidomni.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.services.subscribers.ClipboardSubscriber;
import com.omnipaste.droidomni.services.subscribers.GcmWorkaroundSubscriber;
import com.omnipaste.droidomni.services.subscribers.PhoneSubscriber;
import com.omnipaste.droidomni.services.subscribers.Subscriber;
import com.omnipaste.droidomni.services.subscribers.TelephonyNotificationsSubscriber;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EService
public class OmniService extends Service {
  public static final int MSG_REGISTER_CLIENT = 1;
  public static final int MSG_UNREGISTER_CLIENT = 2;
  public static final int MSG_CREATE_ERROR = 3;
  public static final int MSG_STARTED = 4;
  public static final int MSG_REFRESH_OMNI_CLIPBOARD = 5;

  private List<Subscriber> subscribes = new ArrayList<>();
  private List<Messenger> clients = new ArrayList<>();
  private boolean started = false;

  private final Messenger messenger = new Messenger(new IncomingHandler());

  private class IncomingHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_REGISTER_CLIENT:
          clients.add(msg.replyTo);

          if (started) {
            sendStartedToClient(msg.replyTo);
          }

          break;
        case MSG_UNREGISTER_CLIENT:
          clients.remove(msg.replyTo);
          break;
        case MSG_REFRESH_OMNI_CLIPBOARD:
          if (clipboardSubscriber.get() != null) {
            clipboardSubscriber.get().refreshOmni();
          }
          break;
      }
    }
  }

  @StringRes
  public String appName;

  @Inject
  public ConfigurationService configurationService;

  @Inject
  public Lazy<ClipboardSubscriber> clipboardSubscriber;

  @Inject
  public Lazy<PhoneSubscriber> phoneSubscribe;

  @Inject
  public Lazy<GcmWorkaroundSubscriber> gcmWorkaroundSubscriber;

  @Inject
  public Lazy<TelephonyNotificationsSubscriber> telephonyNotificationsSubscriber;

  @Inject
  public NotificationService notificationService;

  @Inject
  public DeviceService deviceService;

  public static Intent getIntent() {
    return new Intent(DroidOmniApplication.getAppContext(), OmniService_.class);
  }

  public static void restart(final Context context) {
    context.stopService(getIntent());
    context.startService(getIntent());
  }

  public OmniService() {
    DroidOmniApplication.inject(this);
  }

  @Override
  public void onCreate() {
    deviceService.init()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // OnNext
            new Action1<RegisteredDeviceDto>() {
              @Override
              public void call(RegisteredDeviceDto registeredDeviceDto) {
                notifyUser();
                startSubscribers(registeredDeviceDto);
                sendStartedToClients();

                started = true;
              }
            },
            // OnError
            new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                sendErrorToClients(throwable);
              }
            }
        );
  }

  @Override
  public IBinder onBind(Intent intent) {
    return messenger.getBinder();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    stopSubscribers();
    stopForeground(true);
    started = false;
  }

  public List<Subscriber> getSubscribers() {
    if (subscribes.isEmpty()) {
      if (configurationService.isClipboardNotificationEnabled()) {
        subscribes.add(clipboardSubscriber.get());
      }

      if (configurationService.isTelephonyServiceEnabled()) {
        subscribes.add(phoneSubscribe.get());
      }

      if (configurationService.isTelephonyNotificationEnabled()) {
        subscribes.add(telephonyNotificationsSubscriber.get());
      }

      if (configurationService.isGcmWorkAroundEnabled()) {
        subscribes.add(gcmWorkaroundSubscriber.get());
      }
    }

    return subscribes;
  }

  private void startSubscribers(RegisteredDeviceDto registeredDeviceDto) {
    for (Subscriber subscribe : getSubscribers()) {
      subscribe.start(registeredDeviceDto.getIdentifier());
    }
  }

  private void stopSubscribers() {
    for (Subscriber subscribe : getSubscribers()) {
      subscribe.stop();
    }
  }

  private void sendErrorToClients(Throwable throwable) {
    sendMessageToClients(MSG_CREATE_ERROR, throwable);
  }

  private void sendStartedToClients() {
    sendMessageToClients(MSG_STARTED);
  }

  private void sendStartedToClient(Messenger client) {
    Message message = Message.obtain(null, MSG_STARTED);
    try {
      client.send(message);
    } catch (RemoteException ignored) {
    }
  }

  private void sendMessageToClients(int what) {
    sendMessageToClients(what, null);
  }

  private void sendMessageToClients(int what, Object obj) {
    Message message = Message.obtain(null, what);
    message.obj = obj;
    for (Messenger client : clients) {
      try {
        client.send(message);
      } catch (RemoteException e) {
        clients.remove(client);
      }
    }
  }

  private void notifyUser() {
    startForeground(NotificationServiceImpl.NOTIFICATION_ID, notificationService.buildUserNotification(DroidOmniApplication.getAppContext(), appName, ""));
  }
}
