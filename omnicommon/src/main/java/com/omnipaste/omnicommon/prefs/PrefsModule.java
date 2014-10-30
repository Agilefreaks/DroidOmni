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
    return context.getSharedPreferences(getConfigurationFileName(BuildConfig.BUILD_TYPE), MODE_PRIVATE);
  }

  public String getConfigurationFileName(String buildType) {
    return buildType.equals("release") ? LOCAL_CONFIGURATION_FILE_NAME : LOCAL_CONFIGURATION_FILE_NAME + "." + buildType;
  }
}
