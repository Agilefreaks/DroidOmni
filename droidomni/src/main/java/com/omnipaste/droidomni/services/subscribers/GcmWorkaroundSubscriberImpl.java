package com.omnipaste.droidomni.services.subscribers;

import com.omnipaste.droidomni.services.DeviceService;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GcmWorkaroundSubscriberImpl implements GcmWorkaroundSubscriber {
  private Subscription subscriber;

  @Override
  public void start(String deviceIdentifier) {
    subscriber = Observable.timer(0, 2, TimeUnit.MINUTES, Schedulers.io()).subscribe(new Action1<Long>() {
      @Override
      public void call(Long aLong) {
        new DeviceService().registerToGcm()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
      }
    });
  }

  @Override
  public void stop() {
    subscriber.unsubscribe();
  }
}
