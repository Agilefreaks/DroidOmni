package com.omnipaste.droidomni.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.omnipaste.droidomni.R;

public class SettingsActivity extends PreferenceActivity {

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
  public boolean isValidFragment(String fragmentName) {
    return true;
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
