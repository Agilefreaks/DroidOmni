package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;

@Singleton
public class ClipboardSubscriber implements Subscriber {
  private Subscription clipboardSubscriber;
  private ClipboardProvider clipboardProvider;
  private ReplaySubject<ClippingDto> subject;

  @Inject
  public ClipboardSubscriber(ClipboardProvider clipboardProvider) {
    this.clipboardProvider = clipboardProvider;
    subject = ReplaySubject.create();
  }

  @Override
  public void start(String deviceIdentifier) {
    clipboardSubscriber = clipboardProvider
        .init(deviceIdentifier)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<ClippingDto>() {
          @Override
          public void call(ClippingDto clippingDto) {
            subject.onNext(clippingDto);
          }
        });
  }

  @Override
  public void stop() {
    if (clipboardSubscriber != null) {
      clipboardSubscriber.unsubscribe();
      clipboardProvider.destroy();
    }
  }

  public void subscribe(Observer<ClippingDto> observer) {
    subject.subscribe(observer);
  }

  public void refresh() {
    clipboardProvider.refreshOmni();
  }
}
