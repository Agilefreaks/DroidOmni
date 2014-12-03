package com.omnipaste.droidomni.presenter;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.domain.Clipping;
import com.omnipaste.droidomni.factory.NotificationFactory;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.ReplaySubject;

@Singleton
public class ClippingsPresenter extends Presenter<ClippingsPresenter.View> implements Observer<ClippingDto> {
  private final NotificationFactory notificationFactory;
  private final NotificationManagerCompat notificationManager;
  private final ReplaySubject<Clipping> clippingsSubject = ReplaySubject.create();
  private ClipboardSubscriber clipboardSubscriber;
  private Subscription clipboardSubscription;

  public interface View {
  }

  @Inject
  public ClippingsPresenter(
      ClipboardSubscriber clipboardSubscriber,
      NotificationFactory notificationFactory,
      NotificationManagerCompat notificationManager
  ) {
    this.clipboardSubscriber = clipboardSubscriber;
    this.notificationFactory = notificationFactory;
    this.notificationManager = notificationManager;
  }

  @Override
  public void initialize() {
    if (clipboardSubscription != null) {
      return;
    }

    clipboardSubscription = clipboardSubscriber.subscribe(this);
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
    clipboardSubscription.unsubscribe();
    clipboardSubscription = null;
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(ClippingDto clippingDto) {
    clippingsSubject.onNext(Clipping.add(clippingDto));

    Notification notification;
    if (clippingDto.getType() == ClippingDto.ClippingType.UNKNOWN) {
      notification = notificationFactory.buildSimpleNotification(DroidOmniApplication.getAppContext(), clippingDto);
    } else {
      notification = notificationFactory.buildSmartActionNotification(DroidOmniApplication.getAppContext(), clippingDto);
    }

    notificationManager.notify(NotificationFactory.NOTIFICATION_ID, notification);
  }

  public Observable<Clipping> getObservable() {
    return clippingsSubject;
  }

  public void remove(ClippingDto clippingDto) {
    clippingsSubject.onNext(Clipping.remove(clippingDto));
  }
}
