package com.omnipaste.droidomni.service.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.factory.NotificationFactory;
import com.omnipaste.droidomni.service.ServiceBase;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;
import rx.Subscription;

@Singleton
public class NotificationServiceClippings extends ServiceBase implements Observer<ClippingDto> {
  private ClipboardProvider clipboardProvider;
  private NotificationFactory notificationFactory;
  private NotificationManagerCompat notificationManager;
  private Subscription subscription;

  @Inject
  public NotificationServiceClippings(
    ClipboardProvider clipboardProvider,
    NotificationFactory notificationFactory,
    NotificationManagerCompat notificationManager) {
    this.clipboardProvider = clipboardProvider;
    this.notificationFactory = notificationFactory;
    this.notificationManager = notificationManager;
  }

  @Override
  public void start() {
    if (subscription != null) {
      return;
    }

    subscription = clipboardProvider
      .getObservable()
      .observeOn(getObserveOnScheduler())
      .subscribe(this);
  }

  @Override
  public void stop() {
    subscription.unsubscribe();
    subscription = null;
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(ClippingDto clippingDto) {
    Notification notification;
    if (clippingDto.getType() == ClippingDto.ClippingType.UNKNOWN) {
      notification = notificationFactory.buildSimpleNotification(DroidOmniApplication.getAppContext(), clippingDto);
    } else {
      notification = notificationFactory.buildSmartActionNotification(DroidOmniApplication.getAppContext(), clippingDto);
    }

    notificationManager.notify(NotificationFactory.NOTIFICATION_ID, notification);
  }
}
