package com.omnipaste.droidomni.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.services.subscribers.ClipboardSubscriber;
import com.omnipaste.droidomni.services.subscribers.PhoneSubscriber;
import com.omnipaste.droidomni.services.subscribers.TelephonyNotificationsSubscriber;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.res.StringRes;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EService
public class OmniService extends Service {
  public final static String DEVICE_IDENTIFIER_EXTRA_KEY = "device_identifier";

  private Boolean started = false;
  private String deviceIdentifier;

  @StringRes
  public String appName;

  @Inject
  public ConfigurationService configurationService;

  @Inject
  public ClipboardSubscriber clipboardSubscriber;

  @Inject
  public PhoneSubscriber phoneSubscribe;

  @Inject
  public TelephonyNotificationsSubscriber telephonyNotificationsSubscriber;

  @Inject
  public NotificationService notificationService;

  public static void start(final RegisteredDeviceDto registeredDeviceDto) {
    Intent service = new Intent(DroidOmniApplication.getAppContext(), OmniService_.class);
    service.putExtra(DEVICE_IDENTIFIER_EXTRA_KEY, registeredDeviceDto.getIdentifier());
    DroidOmniApplication.getAppContext().startService(service);
  }

  public static void stop(final Context context) {
    context.stopService(new Intent(context, OmniService_.class));
  }

  public OmniService() {
    super();
    DroidOmniApplication.inject(this);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);

    Bundle extras = intent.getExtras();
    if (extras != null) {
      deviceIdentifier = extras.getString(DEVICE_IDENTIFIER_EXTRA_KEY);
    }

    start();

    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    stop();
  }

  private void start() {
    if (!started) {
      notifyUser();

      clipboardSubscriber.start(deviceIdentifier);
      phoneSubscribe.start(deviceIdentifier);
      telephonyNotificationsSubscriber.start(deviceIdentifier);

      // work around for gcm registration
      Observable.timer(2, TimeUnit.MINUTES, Schedulers.io()).subscribe(new Action1<Long>() {
        @Override
        public void call(Long aLong) {
          new DeviceService().registerToGcm()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe();
        }
      });

      started = true;
    }
  }

  private void stop() {
    if (started) {
      started = false;

      clipboardSubscriber.stop();
      phoneSubscribe.stop();
      telephonyNotificationsSubscriber.stop();

      stopForeground(true);
    }
  }

  private void notifyUser() {
    startForeground(NotificationServiceImpl.NOTIFICATION_ID, notificationService.buildUserNotification(DroidOmniApplication.getAppContext(), appName, ""));
  }
}
