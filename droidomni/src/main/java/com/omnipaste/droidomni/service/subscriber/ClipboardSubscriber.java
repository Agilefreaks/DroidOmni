package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;

@Singleton
public class ClipboardSubscriber extends ObservableSubscriber<ClippingDto> {
  private Subscription clipboardSubscriber;
  private ClipboardProvider clipboardProvider;

  @Inject
  public ClipboardSubscriber(ClipboardProvider clipboardProvider) {
    this.clipboardProvider = clipboardProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    clipboardSubscriber = clipboardProvider
        .init(deviceIdentifier)
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
}
