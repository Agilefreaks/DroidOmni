package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public abstract class NotificationFilter {
  private final NotificationProvider notificationProvider;
  private Subscription subscription;

  protected String deviceId;

  protected NotificationFilter(NotificationProvider notificationProvider) {
    this.notificationProvider = notificationProvider;
  }

  public void init(String deviceId) {
    this.deviceId = deviceId;
    if (subscription != null) {
      return;
    }

    subscription = notificationProvider
      .getObservable()
      .filter(new Func1<NotificationDto, Boolean>() {
        @Override
        public Boolean call(NotificationDto notificationDto) {
          return notificationDto.getType() == getType();
        }
      }).subscribe(
        new Action1<NotificationDto>() {
          @Override
          public void call(NotificationDto notificationDto) {
            execute(notificationDto);
          }
        }
      );
  }

  public void destroy() {
    subscription.unsubscribe();
    subscription = null;
  }

  protected abstract void execute(NotificationDto notificationDto);

  protected abstract NotificationDto.Type getType();
}
