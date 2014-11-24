package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.eventsprovider.TelephonyEventsProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

@Singleton
public class TelephonyEventsSubscriber implements Subscriber {
  private TelephonyEventsProvider telephonyEventsProvider;
  private Subscription subscriber;

  @Inject
  public TelephonyEventsSubscriber(TelephonyEventsProvider telephonyEventsProvider) {
    this.telephonyEventsProvider = telephonyEventsProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    subscriber = telephonyEventsProvider
        .init(deviceIdentifier)
        .subscribe();
  }

  @Override
  public void stop() {
    if (subscriber != null) {
      subscriber.unsubscribe();
      telephonyEventsProvider.destroy();
    }
  }
}
