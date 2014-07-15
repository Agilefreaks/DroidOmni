package com.omnipaste.eventsprovider;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class EventsProviderModule {
  @Provides
  public TelephonyNotificationsProvider providesTelephonyNotificationProvider(TelephonyNotificationsProviderImpl telephonyNotificationsProvider) {
    return telephonyNotificationsProvider;
  }
}
