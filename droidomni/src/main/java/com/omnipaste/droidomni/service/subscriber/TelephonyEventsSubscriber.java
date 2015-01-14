package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.eventsprovider.TelephonyListenerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

@Singleton
public class TelephonyEventsSubscriber implements Subscriber {
  private TelephonyListenerProvider telephonyListenerProvider;
  private Subscription subscriber;

  @Inject
  public TelephonyEventsSubscriber(TelephonyListenerProvider telephonyListenerProvider) {
    this.telephonyListenerProvider = telephonyListenerProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    subscriber = telephonyListenerProvider
        .init(deviceIdentifier)
        .subscribe();
  }

  @Override
  public void stop() {
    if (subscriber != null) {
      subscriber.unsubscribe();
      telephonyListenerProvider.destroy();
    }
  }
}
