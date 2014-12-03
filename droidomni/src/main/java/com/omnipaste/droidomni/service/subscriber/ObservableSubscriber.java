package com.omnipaste.droidomni.service.subscriber;

import rx.Observable;
import rx.subjects.PublishSubject;

public abstract class ObservableSubscriber<T> implements Subscriber {
  protected PublishSubject<T> subject;

  protected ObservableSubscriber() {
    this.subject = PublishSubject.create();
  }

  public Observable<T> getObservable() {
    return subject;
  }
}
