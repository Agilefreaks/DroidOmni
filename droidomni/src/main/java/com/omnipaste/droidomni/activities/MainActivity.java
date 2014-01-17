package com.omnipaste.droidomni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.UpdateUI;
import com.omnipaste.droidomni.factories.FragmentFactory;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {
  private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
  private EventBus eventBus = EventBus.getDefault();

  @Inject
  public ConfigurationService configurationService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DroidOmniApplication.inject(this);

    if (savedInstanceState == null) {
      setFragment(FragmentFactory.create_from(configurationService.getConfiguration()));
    }

    eventBus.register(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    checkPlayServices();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(UpdateUI event) {
    setFragment(FragmentFactory.create_from(configurationService.getConfiguration()));
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case REQUEST_CODE_RECOVER_PLAY_SERVICES:
        if (resultCode == RESULT_CANCELED) {
          Toast.makeText(this, "Google Play Services must be installed.", Toast.LENGTH_SHORT).show();
          finish();
        }
        return;
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  private boolean checkPlayServices() {
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (status != ConnectionResult.SUCCESS) {
      if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
        showErrorDialog(status);
      } else {
        Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
        finish();
      }
      return false;
    }
    return true;
  }

  private void showErrorDialog(int code) {
    GooglePlayServicesUtil.getErrorDialog(code, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
  }

  private void setFragment(Fragment fragment) {
    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.main_container, fragment)
        .commit();
  }
}