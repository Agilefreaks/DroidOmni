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
  public static String NOTIFICATIONS_CLIPBOARD_KEY = "notifications_clipboard";
  public static String NOTIFICATIONS_TELEPHONY_KEY = "notifications_telephony";
  public static String NOTIFICATIONS_PHONE_KEY = "notifications_phone";
  public static String TUTORIAL_CLIPPING_LOCAL = "tutorial_clipping_local";
  public static String TUTORIAL_CLIPPING_CLOUD = "tutorial_clipping_cloud";
  public static String DEVICE_ID = "device_id";
  public static String WE_ARE_ALONE = "we_are_alone";
  public static String START_OMNI_AT_BOOT = "start_omni_at_boot";

  @Provides @Singleton @GcmSenderId
  public StringPreference provideGcmSenderId(SharedPreferences preferences) {
    return new StringPreference(preferences, GCM_SENDER_ID_KEY);
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

  @Provides @Singleton @TutorialClippingLocal
  public BooleanPreference provideTutorialClippingLocal(SharedPreferences preferences) {
    return new BooleanPreference(preferences, TUTORIAL_CLIPPING_LOCAL, true);
  }

  @Provides @Singleton @TutorialClippingCloud
  public BooleanPreference provideTutorialClippingCloud(SharedPreferences preferences) {
    return new BooleanPreference(preferences, TUTORIAL_CLIPPING_CLOUD, true);
  }

  @Provides @Singleton @DeviceId
  public StringPreference provideDeviceId(SharedPreferences preferences) {
    return new StringPreference(preferences, DEVICE_ID, "");
  }

  @Provides @Singleton @WeAreAlone
  public BooleanPreference provideWeAreAlone(SharedPreferences preferences) {
    return new BooleanPreference(preferences, WE_ARE_ALONE, false);
  }

  @Provides @Singleton @StartOmniAtBoot
  public BooleanPreference provideStartOmniAtBoot(SharedPreferences preferences) {
    return new BooleanPreference(preferences, START_OMNI_AT_BOOT, true);
  }
}
