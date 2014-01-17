package com.omnipaste.droidomni.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

public class LocalConfigurationService implements ConfigurationService {
  private final SharedPreferences sharedPreferences;
  private Configuration configuration;

  public static String CHANNEL_KEY = "channel";
  public static String GCM_SENDER_ID_KEY = "gcmSenderId";
  public static String API_URL_KEY = "apiUrl";

  public LocalConfigurationService(Context context) {
    sharedPreferences = context.getSharedPreferences("com.omnipaste.droidomni", Context.MODE_PRIVATE);
  }

  @Override
  public Configuration getConfiguration() {
    Configuration result;

    if (configuration != null) {
      result = configuration;
    }
    else {
      result = new Configuration();
      result.channel = sharedPreferences.getString(CHANNEL_KEY, "");
      result.gcmSenderId = sharedPreferences.getString(GCM_SENDER_ID_KEY,  "");
      result.apiUrl = sharedPreferences.getString(API_URL_KEY,  "");
    }

    return result;
  }

  @Override
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;

    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(CHANNEL_KEY, configuration.channel);
    editor.putString(GCM_SENDER_ID_KEY, configuration.gcmSenderId);
    editor.putString(API_URL_KEY, configuration.apiUrl);
    editor.commit();
  }
}
