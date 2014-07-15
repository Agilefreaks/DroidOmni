package com.omnipaste.droidomni.services.subscribers;

import com.omnipaste.eventsprovider.TelephonyNotificationsProvider;

import javax.inject.Inject;

import rx.Subscription;

public class TelephonyNotificationsSubscriberImpl implements TelephonyNotificationsSubscriber {
  private TelephonyNotificationsProvider telephonyNotificationsProvider;
  private Subscription subscriber;

  @Inject
  public TelephonyNotificationsSubscriberImpl(TelephonyNotificationsProvider telephonyNotificationsProvider) {
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
    subscriber.unsubscribe();
    telephonyNotificationsProvider.destroy();
  }
}
