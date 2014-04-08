package com.omnipaste.droidomni.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

public class LocalConfigurationService implements ConfigurationService, SharedPreferences.OnSharedPreferenceChangeListener {
  private final SharedPreferences sharedPreferences;
  private Configuration configuration;

  public static String GCM_SENDER_ID_KEY = "gcmSenderId";
  public static String API_URL_KEY = "apiUrl";
  public static String API_CLIENT_ID = "clientId";
    public static String ACCESS_TOKEN = "accessToken";

  public LocalConfigurationService(Context context) {
    sharedPreferences = context.getSharedPreferences("com.omnipaste.droidomni", Context.MODE_PRIVATE);
    sharedPreferences.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public Configuration getConfiguration() {
    if (configuration == null) {
      populateConfiguration();
    }

    return configuration;
  }

  @Override
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;

    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(GCM_SENDER_ID_KEY, configuration.getGcmSenderId());
    editor.putString(API_URL_KEY, configuration.getApiUrl());
    editor.putString(API_CLIENT_ID, configuration.getApiClientId());
    editor.putString(ACCESS_TOKEN, new Gson().toJson(configuration.getAccessToken()));

    editor.commit();
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
    populateConfiguration();
  }

  private void populateConfiguration() {
    configuration = new Configuration();

    configuration.setGcmSenderId(sharedPreferences.getString(GCM_SENDER_ID_KEY, ""));
    configuration.setApiUrl(sharedPreferences.getString(API_URL_KEY, ""));
    configuration.setApiClientId(sharedPreferences.getString(API_CLIENT_ID, ""));
    configuration.setAccessToken(
        new Gson().fromJson(sharedPreferences.getString(ACCESS_TOKEN, ""), AccessTokenDto.class));
  }
}
