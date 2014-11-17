package com.omnipaste.droidomni.presenter;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.adapter.ClippingAdapter;
import com.omnipaste.droidomni.service.NotificationService;
import com.omnipaste.droidomni.service.SmartActionService;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

@Singleton
public class ClippingPresenter extends Presenter<ClippingPresenter.View> implements Observer<ClippingDto> {

  private final ClippingAdapter clippingAdapter;
  private final ClipboardSubscriber clipboardSubscriber;
  private final SmartActionService smartActionService;
  private final NotificationService notificationService;
  private final NotificationManagerCompat notificationManager;

  public interface View {
  }

  @Inject
  public ClippingPresenter(
      ClipboardSubscriber clipboardSubscriber,
      ClippingAdapter clippingAdapter,
      SmartActionService smartActionService,
      NotificationService notificationService,
      NotificationManagerCompat notificationManager
  ) {
    this.clipboardSubscriber = clipboardSubscriber;
    this.smartActionService = smartActionService;
    this.notificationService = notificationService;
    this.notificationManager = notificationManager;
    this.clippingAdapter = clippingAdapter;

    clipboardSubscriber.subscribe(this);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);

    Notification notification;
    if (clippingDto.getType() == ClippingDto.ClippingType.UNKNOWN) {
      notification = notificationService.buildSimpleNotification(DroidOmniApplication.getAppContext(), clippingDto);
    } else {
      notification = notificationService.buildSmartActionNotification(DroidOmniApplication.getAppContext(), clippingDto);
    }

    notificationManager.notify(NotificationService.NOTIFICATION_ID, notification);
  }

  public ClippingAdapter getClippingAdapter() {
    return clippingAdapter;
  }

  public void remove(ClippingDto item) {
    clippingAdapter.remove(item);
  }

  public void smartAction(ClippingDto item) {
    smartActionService.run(item);
  }

  public void refresh() {
    clipboardSubscriber.refresh();
  }
}
