package com.omnipaste.droidomni.service.subscriber;

import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

public abstract class ObservableSubscriber<T> implements Subscriber {
  protected PublishSubject<T> subject;

  protected ObservableSubscriber() {
    this.subject = PublishSubject.create();
  }

  public Subscription subscribe(Observer<T> observer) {
    return subject.subscribe(observer);
  }
}
