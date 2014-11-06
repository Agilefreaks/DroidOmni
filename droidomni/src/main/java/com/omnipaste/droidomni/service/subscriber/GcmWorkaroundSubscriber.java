package com.omnipaste.droidomni.service.subscriber;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

// TODO: FIX this should probably be a service
@Singleton
public class GcmWorkaroundSubscriber implements Subscriber {
  private Subscription subscriber;

  @Inject
  public GcmWorkaroundSubscriber() {
  }

  @Override
  public void start(String deviceIdentifier) {
//    subscriber = Observable.timer(2, 2, TimeUnit.MINUTES, Schedulers.io()).subscribe(new Action1<Long>() {
//      @Override
//      public void call(Long aLong) {
//        new DeviceService().registerToGcm()
//            .subscribeOn(Schedulers.io())
//            .observeOnScheduler(AndroidSchedulers.mainThread())
//            .subscribe(
//                // onNext
//                new Action1<String>() {
//                  @Override
//                  public void call(String s) {
//                    // nothing
//                  }
//                },
//                // onError
//                new Action1<Throwable>() {
//                  @Override
//                  public void call(Throwable throwable) {
//                    // nothing
//                  }
//                }
//            );
//      }
//    });
  }

  @Override
  public void stop() {
    if (subscriber != null) {
      subscriber.unsubscribe();
    }
  }
}
