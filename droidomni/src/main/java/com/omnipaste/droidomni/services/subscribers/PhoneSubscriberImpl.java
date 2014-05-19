package com.omnipaste.droidomni.services.subscribers;

import com.omnipaste.phoneprovider.PhoneProvider;

import javax.inject.Inject;

import rx.Subscription;

public class PhoneSubscriberImpl implements PhoneSubscriber {
  private PhoneProvider phoneProvider;
  private Subscription subscriber;

  @Inject
  public PhoneSubscriberImpl(PhoneProvider phoneProvider) {
    this.phoneProvider = phoneProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    subscriber = phoneProvider.init(deviceIdentifier).subscribe();
  }

  @Override
  public void stop() {
    subscriber.unsubscribe();
    phoneProvider.destroy();
  }
}
