package com.omnipaste.droidomni.service.subscriber;

import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public abstract class ObservableSubscriber<T> implements Subscriber {
  protected ReplaySubject<T> subject;

  protected ObservableSubscriber() {
    this.subject = ReplaySubject.create();
  }

  public Subscription subscribe(Observer<T> observer) {
    return subject.subscribe(observer);
  }
}
