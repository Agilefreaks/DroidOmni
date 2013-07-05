package com.omnipasteapp.omnipaste.providers;

import android.content.Context;
import android.content.SharedPreferences;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;

import javax.inject.Inject;

public class SharedPreferencesConfigurationProvider implements IConfigurationProvider {
  public static final String SharedPreferenceKey = "omnipaste";

  @Inject
  public Context context;

  @Override
  public String getValue(String key) {
    SharedPreferences preferences = getSharedPreferences();

    return preferences.getString(key, null);
  }

  @Override
  public boolean setValue(String key, String value) {
    SharedPreferences preferences = getSharedPreferences();
    SharedPreferences.Editor editor = preferences.edit();

    editor.putString(key, value);

    return editor.commit();
  }

  protected SharedPreferences getSharedPreferences() {
    return context.getSharedPreferences(SharedPreferenceKey, Context.MODE_PRIVATE);
  }
}
