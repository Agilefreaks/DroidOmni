package com.omnipaste.eventsprovider;

import android.telephony.TelephonyManager;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class EventsProviderModule {
  @Provides
  public TelephonyNotificationsProvider providesTelephonyNotificationProvider(TelephonyManager telephonyManager) {
    return new TelephonyNotificationsProvider(telephonyManager);
  }
}
