package com.omnipaste.omniapi.prefs;

import android.content.SharedPreferences;

import com.omnipaste.omnicommon.prefs.StringPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class PrefsModule {
  public static String API_URL_KEY = "apiUrl";
  public static String API_CLIENT_ID_KEY = "clientId";
  public static String ACCESS_TOKEN_KEY = "accessToken";
  public static String CLIENT_TOKEN_KEY = "clientToken";

  @Provides @Singleton @ApiUrl
  public StringPreference provideApiUrl(SharedPreferences preferences) {
    return new StringPreference(preferences, API_URL_KEY);
  }

  @Provides @Singleton @ApiClientId
  public StringPreference provideApiClientId(SharedPreferences preferences) {
    return new StringPreference(preferences, API_CLIENT_ID_KEY);
  }

  @Provides @Singleton @ApiAccessToken
  public StringPreference provideApiAccessToken(SharedPreferences preferences) {
    return new StringPreference(preferences, ACCESS_TOKEN_KEY);
  }

  @Provides @Singleton @ApiClientToken
  public StringPreference provideApiClientToken(SharedPreferences preferences) {
    return new StringPreference(preferences, CLIENT_TOKEN_KEY);
  }
}
