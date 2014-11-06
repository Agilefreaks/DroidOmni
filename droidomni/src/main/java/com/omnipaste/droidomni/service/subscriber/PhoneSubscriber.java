package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.phoneprovider.PhoneProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

@Singleton
public class PhoneSubscriber implements Subscriber {
  private PhoneProvider phoneProvider;
  private Subscription subscriber;

  @Inject
  public PhoneSubscriber(PhoneProvider phoneProvider) {
    this.phoneProvider = phoneProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    subscriber = phoneProvider.init(deviceIdentifier).subscribe();
  }

  @Override
  public void stop() {
    if (subscriber != null) {
      subscriber.unsubscribe();
      phoneProvider.destroy();
    }
  }
}
