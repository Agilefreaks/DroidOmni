package com.omnipaste.droidomni.service.subscriber;

import com.omnipaste.eventsprovider.TelephonyNotificationsProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

@Singleton
public class TelephonyNotificationsSubscriber implements Subscriber {
  private TelephonyNotificationsProvider telephonyNotificationsProvider;
  private Subscription subscriber;

  @Inject
  public TelephonyNotificationsSubscriber(TelephonyNotificationsProvider telephonyNotificationsProvider) {
    this.telephonyNotificationsProvider = telephonyNotificationsProvider;
  }

  @Override
  public void start(String deviceIdentifier) {
    subscriber = telephonyNotificationsProvider
        .init(deviceIdentifier)
        .subscribe();
  }

  @Override
  public void stop() {
    if (subscriber != null) {
      subscriber.unsubscribe();
      telephonyNotificationsProvider.destroy();
    }
  }
}
