package com.omnipaste.droidomni.controllers;

import android.app.NotificationManager;

import com.omnipaste.droidomni.service.NotificationService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.Collection;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.ReplaySubject;

public class ClippingsFragmentControllerImpl implements ClippingsFragmentController {
  private ReplaySubject<ClippingDto> clippingsSubject;

  @Inject
  public NotificationService notificationService;

  @Inject
  public NotificationManager notificationManager;

  public ClippingsFragmentControllerImpl() {
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
    return null;
  }

  public void setClipping(ClippingDto clippingDto) {
    clippingsSubject.onNext(clippingDto);
  }
}
