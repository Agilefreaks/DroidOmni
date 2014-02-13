package com.omnipaste.droidomni.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.omnipaste.clipboardprovider.IClipboardProvider;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.services.ConfigurationService;
import com.omnipaste.phoneprovider.IPhoneProvider;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import rx.util.functions.Action1;

@EService
public class OmniService extends Service {
  public final static String DEVICE_IDENTIFIER_EXTRA_KEY = "device_identifier";

  private EventBus eventBus = EventBus.getDefault();
  private Boolean started = false;
  private String deviceIdentifier;
  private Subscription phoneSubscribe;
  private Subscription clipboardSubscriber;

  @StringRes
  public String appName;

  @Inject
  public ConfigurationService configurationService;

  @Inject
  public IClipboardProvider clipboardProvider;

  @Inject
  public IPhoneProvider phoneProvider;

  public static Observable start(final Context context, final RegisteredDeviceDto registeredDeviceDto) {
    return Observable.create(new Observable.OnSubscribeFunc() {
      @Override
      public Subscription onSubscribe(Observer observer) {
        Intent service = new Intent(context, OmniService_.class);
        service.putExtra(DEVICE_IDENTIFIER_EXTRA_KEY, registeredDeviceDto.getIdentifier());
        context.startService(service);

        observer.onCompleted();

        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.immediate());
  }

  public static Observable stop(final Context context) {
    return Observable.create(new Observable.OnSubscribeFunc() {
      @Override
      public Subscription onSubscribe(Observer observer) {
        context.stopService(new Intent(context, OmniService_.class));

        observer.onCompleted();

        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.immediate());
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

      Configuration configuration = configurationService.getConfiguration();

      clipboardSubscriber = clipboardProvider
          .getObservable(configuration.getChannel(), deviceIdentifier)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<ClippingDto>() {
            @Override
            public void call(ClippingDto clippingDto) {
              eventBus.post(new ClippingAdded(clippingDto));
            }
          });

      phoneSubscribe = phoneProvider.getObservable(getApplicationContext()).subscribe();

      started = true;
    }
  }

  private void stop() {
    if (started) {
      started = false;
      phoneSubscribe.unsubscribe();
      clipboardSubscriber.unsubscribe();
      stopForeground(true);
    }
  }

  private void notifyUser() {
    final int serviceId = 42;

    Intent resultIntent = new Intent(this, MainActivity_.class);
    resultIntent.setAction("android.intent.action.MAIN");
    resultIntent.addCategory("android.intent.category.LAUNCHER");

    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_stat_clipboard)
        .setContentTitle(appName)
        .setContentText("")
        .setOngoing(true)
        .setContentIntent(contentIntent);

    startForeground(serviceId, builder.build());
  }
}
