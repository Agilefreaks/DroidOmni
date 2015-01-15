package com.omnipaste.phoneprovider.receivers;

import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.NotificationFilter;

import rx.Observable;
import rx.subjects.PublishSubject;

public abstract class BaseReceiver<T> extends NotificationFilter {
  protected final PublishSubject<T> subject;

  protected BaseReceiver(NotificationProvider notificationProvider) {
    super(notificationProvider);

    subject = PublishSubject.create();
  }

  public Observable<T> getObservable() {
    return subject;
  }
}
