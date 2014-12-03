package com.omnipaste.droidomni.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.factory.NotificationFactory;
import com.omnipaste.droidomni.interaction.ActivateDevice;
import com.omnipaste.droidomni.interaction.DeactivateDevice;
import com.omnipaste.droidomni.prefs.GcmWorkaround;
import com.omnipaste.droidomni.prefs.NotificationsClipboard;
import com.omnipaste.droidomni.prefs.NotificationsPhone;
import com.omnipaste.droidomni.prefs.NotificationsTelephony;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.droidomni.service.subscriber.EventsSubscriber;
import com.omnipaste.droidomni.service.subscriber.GcmWorkaroundSubscriber;
import com.omnipaste.droidomni.service.subscriber.PhoneSubscriber;
import com.omnipaste.droidomni.service.subscriber.ScreenOnSubscriber;
import com.omnipaste.droidomni.service.subscriber.Subscriber;
import com.omnipaste.droidomni.service.subscriber.TelephonyEventsSubscriber;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.Lazy;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EService
public class OmniService extends Service {
  private final OmniServiceIncomingHandler omniServiceIncomingHandler = new OmniServiceIncomingHandler(this);
  private final Messenger messenger = new Messenger(omniServiceIncomingHandler);

  private List<Subscriber> subscribes = new ArrayList<>();

  public AtomicBoolean started = new AtomicBoolean(false);

  @StringRes
  public String appName;

  @Inject
  public Lazy<ClipboardSubscriber> clipboardSubscriber;

  @Inject
  public Lazy<PhoneSubscriber> phoneSubscribe;

  @Inject
  public Lazy<GcmWorkaroundSubscriber> gcmWorkaroundSubscriber;

  @Inject
  public Lazy<TelephonyEventsSubscriber> telephonyNotificationsSubscriber;

  @Inject
  public Lazy<ScreenOnSubscriber> screenOnSubscriber;

  @Inject
  public Lazy<EventsSubscriber> eventsSubscriber;

  @Inject
  public SessionService sessionService;

  @Inject
  public ActivateDevice activateDevice;

  @Inject
  public DeactivateDevice deactivateDevice;

  @Inject @NotificationsClipboard
  public BooleanPreference isClipboardNotificationEnabled;

  @Inject @NotificationsTelephony
  public BooleanPreference isTelephonyNotificationEnabled;

  @Inject @NotificationsPhone
  public BooleanPreference isPhoneNotificationEnabled;

  @Inject @GcmWorkaround
  public BooleanPreference isGcmWorkAroundEnabled;

  @Inject
  public NotificationFactory notificationFactory;

  public static Intent getIntent() {
    return new Intent(DroidOmniApplication.getAppContext(), OmniService_.class);
  }

  public OmniService() {
    DroidOmniApplication.inject(this);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return messenger.getBinder();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    activateDevice.run()
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

                started.set(true);
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

    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    if (!started.get()) {
      return;
    }

    deactivateDevice.run()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // onNext
            new Action1<Object>() {
              @Override
              public void call(Object param) {
                cleanup();
                sendStoppedToClients();
              }
            },
            // OnError
            new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                cleanup();
                sendErrorToClients(throwable);
              }
            }
        );
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

      subscribes.add(screenOnSubscriber.get());
      subscribes.add(eventsSubscriber.get());
    }

    return subscribes;
  }

  public boolean isStarted() {
    return started.get();
  }

  private void cleanup() {
    sessionService.setRegisteredDeviceDto(null);
    stopForeground(true);
    stopSubscribers();

    started.set(false);
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
    omniServiceIncomingHandler.sendErrorToClients(throwable);
  }

  private void sendStartedToClients() {
    omniServiceIncomingHandler.sendStartedToClients();
  }

  private void sendStoppedToClients() {
    omniServiceIncomingHandler.sendStoppedToClients();
  }

  private void notifyUser() {
    startForeground(NotificationFactory.NOTIFICATION_ID, notificationFactory.buildUserNotification(DroidOmniApplication.getAppContext(), appName, ""));
  }
}
