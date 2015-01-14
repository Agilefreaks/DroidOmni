package com.omnipaste.droidomni.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.factory.NotificationFactory;
import com.omnipaste.droidomni.interaction.ActivateDevice;
import com.omnipaste.droidomni.interaction.DeactivateDevice;
import com.omnipaste.droidomni.prefs.NotificationsClipboard;
import com.omnipaste.droidomni.prefs.NotificationsPhone;
import com.omnipaste.droidomni.prefs.NotificationsTelephony;
import com.omnipaste.eventsprovider.TelephonyProviderFacade;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.DeviceDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;
import com.omnipaste.phoneprovider.PhoneProvider;

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

  private List<Provider> providers = new ArrayList<>();

  public AtomicBoolean started = new AtomicBoolean(false);

  @StringRes
  public String appName;

  @Inject
  public Lazy<ClipboardProvider> clipboardProvider;

  @Inject
  public Lazy<PhoneProvider> phoneProvider;

  @Inject
  public Lazy<TelephonyProviderFacade> telephonyProviderFacade;

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

  @Inject
  public NotificationFactory notificationFactory;

  @Inject
  public NotificationServiceFacade notificationServiceFacade;

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
            new Action1<DeviceDto>() {
              @Override
              public void call(DeviceDto deviceDto) {
                notifyUser();
                init(deviceDto);
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

  public List<Provider> getProviders() {
    if (providers.isEmpty()) {
      if (isClipboardNotificationEnabled.get()) {
        providers.add(clipboardProvider.get());
      }

      if (isPhoneNotificationEnabled.get()) {
        providers.add(phoneProvider.get());
      }

      if (isTelephonyNotificationEnabled.get()) {
        providers.add(telephonyProviderFacade.get());
      }
    }

    return providers;
  }

  public boolean isStarted() {
    return started.get();
  }

  private void cleanup() {
    stopForeground(true);
    destroy();

    started.set(false);
  }

  private void init(DeviceDto deviceDto) {
    for (Provider provider : getProviders()) {
      provider.init(deviceDto.getId());
    }

    notificationServiceFacade.start();
  }

  private void destroy() {
    for (Provider provider : getProviders()) {
      provider.destroy();
    }

    notificationServiceFacade.stop();
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
