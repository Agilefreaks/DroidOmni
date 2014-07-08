package com.omnipaste.droidomni.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

public class LocalConfigurationService implements ConfigurationService, SharedPreferences.OnSharedPreferenceChangeListener {
  public static String GCM_SENDER_ID_KEY = "gcmSenderId";
  public static String API_URL_KEY = "apiUrl";
  public static String API_CLIENT_ID = "clientId";
  public static String ACCESS_TOKEN = "accessToken";

  public static String NOTIFICATIONS_CLIPBOARD = "notifications_clipboard";
  public static String NOTIFICATIONS_TELEPHONY = "notifications_telephony";
  public static String NOTIFICATIONS_PHONE = "notifications_phone";
  public static String NOTIFICATIONS_GCM_WORKAROUND = "notifications_gcm_workaround";

  public static String LOCAL_CONFIGURATION_FILE_NAME = "com.omnipaste.droidomni";

  private final SharedPreferences sharedPreferences;
  private Configuration configuration;

  public LocalConfigurationService(Context context) {
    sharedPreferences = context.getSharedPreferences(LOCAL_CONFIGURATION_FILE_NAME, Context.MODE_PRIVATE);
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

    editor.apply();
  }

  @Override
  public boolean isClipboardNotificationEnabled() {
    return getConfiguration().getNotificationsClipboard();
  }

  @Override
  public boolean isTelephonyServiceEnabled() {
    return getConfiguration().getNotificationsPhone();
  }

  @Override
  public boolean isTelephonyNotificationEnabled() {
    return getConfiguration().getNotificationsTelephony();
  }

  @Override
  public boolean isGcmWorkAroundEnabled() {
    return getConfiguration().getNotificationsGcmWorkaround();
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

    configuration.setNotificationsClipboard(sharedPreferences.getBoolean(NOTIFICATIONS_CLIPBOARD, true));
    configuration.setNotificationsTelephony(sharedPreferences.getBoolean(NOTIFICATIONS_TELEPHONY, true));
    configuration.setNotificationsPhone(sharedPreferences.getBoolean(NOTIFICATIONS_PHONE, true));
    configuration.setNotificationsGcmWorkaround(sharedPreferences.getBoolean(NOTIFICATIONS_GCM_WORKAROUND, true));
  }
}
