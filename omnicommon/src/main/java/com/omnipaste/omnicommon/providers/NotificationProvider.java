package com.omnipaste.omnicommon.providers;

import com.omnipaste.omnicommon.dto.NotificationDto;

import rx.Observable;

public interface NotificationProvider {
  public Observable<NotificationDto> getObservable();

  public void post(NotificationDto notificationDto);
}
