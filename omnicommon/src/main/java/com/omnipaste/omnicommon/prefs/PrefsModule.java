package com.omnipaste.omnicommon.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.omnipaste.omnicommon.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module(complete = false, library = true)
public class PrefsModule {
  public static String LOCAL_CONFIGURATION_FILE_NAME = "com.omnipaste.droidomni";

  @Provides @Singleton
  public SharedPreferences provideSharedPreferences(Context context) {
    return context.getSharedPreferences(LOCAL_CONFIGURATION_FILE_NAME, MODE_PRIVATE);
  }
}
