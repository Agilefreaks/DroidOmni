package com.omnipaste.droidomni.prefs;

import android.content.SharedPreferences;

import com.omnipaste.omnicommon.prefs.BooleanPreference;
import com.omnipaste.omnicommon.prefs.StringPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class PrefsModule {
  public static String GCM_SENDER_ID_KEY = "gcmSenderId";
  public static String GCM_WORKAROUND_KEY = "notifications_gcm_workaround";
  public static String NOTIFICATIONS_CLIPBOARD_KEY = "notifications_clipboard";
  public static String NOTIFICATIONS_TELEPHONY_KEY = "notifications_telephony";
  public static String NOTIFICATIONS_PHONE_KEY = "notifications_phone";

  @Provides @Singleton @GcmSenderId
  public StringPreference provideGcmSenderId(SharedPreferences preferences) {
    return new StringPreference(preferences, GCM_SENDER_ID_KEY);
  }

  @Provides @Singleton @GcmSenderId
  public BooleanPreference provideGcmWorkaround(SharedPreferences preferences) {
    return new BooleanPreference(preferences, GCM_WORKAROUND_KEY, true);
  }

  @Provides @Singleton @NotificationsClipboard
  public BooleanPreference provideNotificationsClipboard(SharedPreferences preferences) {
    return new BooleanPreference(preferences, NOTIFICATIONS_CLIPBOARD_KEY, true);
  }

  @Provides @Singleton @NotificationsTelephony
  public BooleanPreference provideNotificationsTelephony(SharedPreferences preferences) {
    return new BooleanPreference(preferences, NOTIFICATIONS_TELEPHONY_KEY, true);
  }

  @Provides @Singleton @NotificationsPhone
  public BooleanPreference provideNotificationsPhone(SharedPreferences preferences) {
    return new BooleanPreference(preferences, NOTIFICATIONS_PHONE_KEY, true);
  }
}
