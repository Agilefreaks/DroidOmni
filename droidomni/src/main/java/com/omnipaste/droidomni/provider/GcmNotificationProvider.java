package com.omnipaste.droidomni.provider;

import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

public class GcmNotificationProvider implements  NotificationProvider {
  private PublishSubject<NotificationDto> notificationSubject;

  @Inject
  public GcmNotificationProvider() {
    notificationSubject = PublishSubject.create();
  }

  public void post(NotificationDto notificationDto) {
    notificationSubject.onNext(notificationDto);
  }

  @Override
  public Observable<NotificationDto> getObservable() {
    return notificationSubject;
  }
}
