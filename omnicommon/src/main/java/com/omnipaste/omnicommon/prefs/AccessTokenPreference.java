package com.omnipaste.omnicommon.prefs;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

public class AccessTokenPreference {
  private final SharedPreferences preferences;
  private final String key;
  private final String defaultValue;

  public AccessTokenPreference(SharedPreferences preferences, String key) {
    this(preferences, key, null);
  }

  public AccessTokenPreference(SharedPreferences preferences, String key, String defaultValue) {
    this.preferences = preferences;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  public AccessTokenDto get() {
    return new Gson().fromJson(preferences.getString(key, defaultValue), AccessTokenDto.class);
  }

  public boolean isSet() {
    return preferences.contains(key);
  }

  public void set(AccessTokenDto value) {
    preferences.edit().putString(key, new Gson().toJson(value)).apply();
  }

  public void delete() {
    preferences.edit().remove(key).apply();
  }
}
