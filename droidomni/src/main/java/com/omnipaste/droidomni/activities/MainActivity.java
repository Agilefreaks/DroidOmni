package com.omnipaste.droidomni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.controllers.MainActivityController;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {
  private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

  @Inject
  public MainActivityController controller;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DroidOmniApplication.inject(this);

    controller.run(this, savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();
    checkPlayServices();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    controller.stop();
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
}