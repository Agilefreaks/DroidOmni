package com.omnipaste.droidomni.prefs;

import android.content.SharedPreferences;

import com.omnipaste.omnicommon.prefs.GcmSenderId;
import com.omnipaste.omnicommon.prefs.StringPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class PrefsModule {
  public static String GCM_SENDER_ID_KEY = "gcmSenderId";

  @Provides @Singleton @GcmSenderId
  public StringPreference provideGcmSenderId(SharedPreferences preferences) {
    return new StringPreference(preferences, GCM_SENDER_ID_KEY);
  }
}
