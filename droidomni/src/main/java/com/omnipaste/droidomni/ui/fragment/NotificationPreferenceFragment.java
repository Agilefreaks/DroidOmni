package com.omnipaste.droidomni.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.SettingsPresenter;
import com.omnipaste.omnicommon.prefs.PrefsModule;

import static android.content.Context.MODE_PRIVATE;

public class NotificationPreferenceFragment extends PreferenceFragment {
  private SettingsPresenter settingsPresenter;

  public NotificationPreferenceFragment() {
  }

  @SuppressLint("ValidFragment")
  public NotificationPreferenceFragment(SettingsPresenter settingsPresenter) {
    this.settingsPresenter = settingsPresenter;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getPreferenceManager().setSharedPreferencesName(PrefsModule.LOCAL_CONFIGURATION_FILE_NAME);
    getPreferenceManager().setSharedPreferencesMode(MODE_PRIVATE);

    addPreferencesFromResource(R.xml.pref_notification);
  }

  @Override
  public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
    if (preference.getKey() != null && preference.getKey().equals("logout")) {
      settingsPresenter.logout();
      return true;
    } else {
      return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
  }
}
