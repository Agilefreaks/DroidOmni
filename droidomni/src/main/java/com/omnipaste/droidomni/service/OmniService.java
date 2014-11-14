package com.omnipaste.droidomni.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.prefs.GcmWorkaround;
import com.omnipaste.droidomni.prefs.NotificationsClipboard;
import com.omnipaste.droidomni.prefs.NotificationsPhone;
import com.omnipaste.droidomni.prefs.NotificationsTelephony;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.droidomni.service.subscriber.GcmWorkaroundSubscriber;
import com.omnipaste.droidomni.service.subscriber.PhoneSubscriber;
import com.omnipaste.droidomni.service.subscriber.Subscriber;
import com.omnipaste.droidomni.service.subscriber.TelephonyNotificationsSubscriber;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

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
  private List<Subscriber> subscribes = new ArrayList<>();
  private List<Messenger> clients = new ArrayList<>();
  private boolean started = false;

  private final Messenger messenger = new Messenger(new OmniIncomingHandler(this));

  @StringRes
  public String appName;

  @Inject
  public Lazy<ClipboardSubscriber> clipboardSubscriber;

  @Inject
  public Lazy<PhoneSubscriber> phoneSubscribe;

  @Inject
  public Lazy<GcmWorkaroundSubscriber> gcmWorkaroundSubscriber;

  @Inject
  public Lazy<TelephonyNotificationsSubscriber> telephonyNotificationsSubscriber;

  @Inject
  public DeviceService deviceService;

  @Inject @NotificationsClipboard
  public BooleanPreference isClipboardNotificationEnabled;

  @Inject @NotificationsTelephony
  public BooleanPreference isTelephonyNotificationEnabled;

  @Inject @NotificationsPhone
  public BooleanPreference isPhoneNotificationEnabled;

  @Inject @GcmWorkaround
  public BooleanPreference isGcmWorkAroundEnabled;

  @Inject
  public NotificationService notificationService;

  public static Intent getIntent() {
    return new Intent(DroidOmniApplication.getAppContext(), OmniService_.class);
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
      if (isClipboardNotificationEnabled.get()) {
        subscribes.add(clipboardSubscriber.get());
      }

      if (isPhoneNotificationEnabled.get()) {
        subscribes.add(phoneSubscribe.get());
      }

      if (isTelephonyNotificationEnabled.get()) {
        subscribes.add(telephonyNotificationsSubscriber.get());
      }

      if (isGcmWorkAroundEnabled.get()) {
        subscribes.add(gcmWorkaroundSubscriber.get());
      }
    }

    return subscribes;
  }

  public void addClient(Messenger replyTo) {
    this.clients.add(replyTo);
  }

  public void removeClient(Messenger replyTo) {
    this.clients.remove(replyTo);
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
    sendMessageToClients(OmniIncomingHandler.MSG_CREATE_ERROR, throwable);
  }

  private void sendStartedToClients() {
    sendMessageToClients(OmniIncomingHandler.MSG_STARTED);
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
    startForeground(NotificationService.NOTIFICATION_ID, notificationService.buildUserNotification(DroidOmniApplication.getAppContext(), appName, ""));
  }

  public boolean isStarted() {
    return started;
  }
}
