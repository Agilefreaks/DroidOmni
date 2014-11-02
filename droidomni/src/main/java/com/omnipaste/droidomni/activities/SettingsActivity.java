package com.omnipaste.droidomni.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.SignOutEvent;
import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.services.OmniService;

import de.greenrobot.event.EventBus;

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
    if (key.equals(PrefsModule.NOTIFICATIONS_CLIPBOARD_KEY) ||
        key.equals(PrefsModule.NOTIFICATIONS_PHONE_KEY) ||
        key.equals(PrefsModule.NOTIFICATIONS_TELEPHONY_KEY) ||
        key.equals(PrefsModule.GCM_WORKAROUND_KEY)) {
      OmniService.restart(DroidOmniApplication.getAppContext());
    }
  }

  public static class NotificationPreferenceFragment extends PreferenceFragment {
    private EventBus eventBus = EventBus.getDefault();

    public NotificationPreferenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      addPreferencesFromResource(R.xml.pref_notification);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
      if (preference.getKey() != null && preference.getKey().equals("logout")) {
        eventBus.post(new SignOutEvent());

        return true;
      }
      else {
        return super.onPreferenceTreeClick(preferenceScreen, preference);
      }
    }
  }
}
