package com.omnipaste.notificationsprovider;

import rx.Observable;

public interface TelephonyNotificationsProvider {
  Observable subscribe(final String identifier);

  void unsubscribe();
}
