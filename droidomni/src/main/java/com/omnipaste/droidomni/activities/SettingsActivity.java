package com.omnipaste.droidomni.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.services.LocalConfigurationService;
import com.omnipaste.droidomni.services.OmniService;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new NotificationPreferenceFragment())
        .commit();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        super.onBackPressed();
        return true;
    }

    return super.onOptionsItemSelected(menuItem);
  }

  @Override
  public void onResume() {
    super.onResume();

    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();

    PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(LocalConfigurationService.NOTIFICATIONS_CLIPBOARD) ||
        key.equals(LocalConfigurationService.NOTIFICATIONS_PHONE) ||
        key.equals(LocalConfigurationService.NOTIFICATIONS_TELEPHONY) ||
        key.equals(LocalConfigurationService.NOTIFICATIONS_GCM_WORKAROUND)) {
      OmniService.restart(DroidOmniApplication.getAppContext());
    }
  }

  public static class NotificationPreferenceFragment extends PreferenceFragment {
    public NotificationPreferenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      addPreferencesFromResource(R.xml.pref_notification);
    }
  }
}
