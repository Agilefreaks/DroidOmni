package com.omnipaste.droidomni.service.subscriber;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public abstract class ObservableSubscriber<T> implements Subscriber {
  protected PublishSubject<T> subject;

  protected ObservableSubscriber() {
    this.subject = PublishSubject.create();
  }

  public Subscription subscribe(Observer<T> observer) {
    return subject
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer);
  }
}
