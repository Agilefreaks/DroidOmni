package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;

import com.google.common.collect.EvictingQueue;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.service.NotificationService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.Collection;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.ReplaySubject;

public class ClippingsFragmentControllerImpl implements ClippingsFragmentController {
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
