package com.omnipaste.droidomni.service;

import android.os.Bundle;

import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

@Singleton
public class ContactsService extends ServiceBase {
  private final NotificationProvider notificationProvider;
  private Subscription subscription;

  @Inject
  public ContactsService(NotificationProvider notificationProvider) {
    this.notificationProvider = notificationProvider;
  }

  @Override
  public void start() {
    if (subscription != null) {
      return;
    }

    subscription = notificationProvider
      .getObservable()
      .filter(new Func1<NotificationDto, Boolean>() {
        @Override
        public Boolean call(NotificationDto notificationDto) {
          return notificationDto.getTarget() == NotificationDto.Target.CONTACTS;
        }
      })
      .subscribe(
        // onNext
        new Action1<NotificationDto>() {
          @Override
          public void call(NotificationDto notificationDto) {
            Bundle extra = notificationDto.getExtra();
            extra.get("source_identifier");
          }
        },
        // onError
        new Action1<Throwable>() {
          @Override
          public void call(Throwable _throwable) {
            // ignore
          }
        }
      );
  }

  @Override
  public void stop() {
    subscription.unsubscribe();
    subscription = null;
  }
}
