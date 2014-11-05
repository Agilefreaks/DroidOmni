package com.omnipaste.droidomni.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.controllers.LauncherActivityControllerImpl;
import com.omnipaste.droidomni.controllers.MainActivityController;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_launcher)
public class LauncherActivity extends ActionBarActivity {
  private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

  public MainActivityController controller;

  public static Intent getIntent(Context context) {
    Intent intent = new Intent(context, LauncherActivity_.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    controller = new LauncherActivityControllerImpl();
//    DroidOmniApplication.inject(controller);

//    if (checkPlayServices()) {
//      controller.run(this, savedInstanceState);
//    }
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    controller.stop();
    controller = null;
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
        Toast.makeText(this, R.string.main_activity_device_not_supported, Toast.LENGTH_LONG).show();
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