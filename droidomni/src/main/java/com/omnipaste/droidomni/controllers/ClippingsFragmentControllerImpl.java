package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;

import com.google.common.collect.EvictingQueue;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.droidomni.events.OmniClipboardRefresh;
import com.omnipaste.droidomni.service.NotificationService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.Collection;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.subjects.ReplaySubject;

public class ClippingsFragmentControllerImpl implements ClippingsFragmentController {
  private final EventBus eventBus = EventBus.getDefault();
  private ReplaySubject<ClippingDto> clippingsSubject;
  private EvictingQueue<ClippingDto> clippings;

  @Inject
  public NotificationService notificationService;

  @Inject
  public NotificationManager notificationManager;

  public ClippingsFragmentControllerImpl() {
    clippings = EvictingQueue.create(42);
    clippingsSubject = ReplaySubject.create();
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @Override public void afterView() {

  }

  @Override
  public Observable<ClippingDto> getObservable() {
    return clippingsSubject;
  }

  @Override
  public Collection<ClippingDto> getClippings() {
    return clippings;
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(ClippingAdded event) {
    ClippingDto clipping = event.getClipping();
    clippings.add(clipping);

    setClipping(clipping);
    notifyClipping(clipping);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(OmniClipboardRefresh event) {
  }

  public void setClipping(ClippingDto clippingDto) {
    clippingsSubject.onNext(clippingDto);
  }

  public void notifyClipping(ClippingDto clipping) {
    Notification notification;
    if (clipping.getType() == ClippingDto.ClippingType.UNKNOWN) {
      notification = notificationService.buildSimpleNotification(DroidOmniApplication.getAppContext(), clipping);
    } else {
      notification = notificationService.buildSmartActionNotification(DroidOmniApplication.getAppContext(), clipping);
    }

    notificationManager.notify(NotificationService.NOTIFICATION_ID, notification);
  }
}
