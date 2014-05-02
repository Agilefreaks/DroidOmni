package com.omnipaste.notificationsprovider;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class NotificationsProviderModule {
  @Provides
  public TelephonyNotificationsProvider providesTelephonyNotificationProvider(TelephonyNotificationsProviderImpl telephonyNotificationsProvider) {
    return telephonyNotificationsProvider;
  }
}
