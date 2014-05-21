package com.omnipaste.droidomni.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

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

@EService
public class OmniService extends Service {
  public final static String DEVICE_IDENTIFIER_EXTRA_KEY = "device_identifier";

  private Boolean started = false;
  private String deviceIdentifier;
  private List<Subscriber> subscribes = new ArrayList<>();

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

      for (Subscriber subscribe : getSubscribers()) {
        subscribe.start(deviceIdentifier);
      }

      started = true;
    }
  }

  private void stop() {
    if (started) {
      started = false;

      for (Subscriber subscribe : getSubscribers()) {
        subscribe.stop();
      }

      stopForeground(true);
    }
  }

  private void notifyUser() {
    startForeground(NotificationServiceImpl.NOTIFICATION_ID, notificationService.buildUserNotification(DroidOmniApplication.getAppContext(), appName, ""));
  }
}
