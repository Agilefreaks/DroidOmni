package com.omnipasteapp.omnipaste.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.services.IIntentHelper;

import javax.inject.Inject;

@NoTitle
@EActivity
public class LaunchActivity extends Activity {
  private static final String TAG = "MainActivity";
  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

  //region Public Properties

  @Inject
  public IConfigurationService configurationService;

  @Inject
  public IIntentHelper intentService;

  //endregion

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);

    checkPlayServices();

    configurationService.initialize();
    if (configurationService.getCommunicationSettings().hasChannel()) {
      finish();
      intentService.startNewActivity(MainActivity_.class);
    } else {
      finish();
      intentService.startNewActivity(LoginActivity_.class);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    checkPlayServices();
  }

  private boolean checkPlayServices() {
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

    if (resultCode != ConnectionResult.SUCCESS) {
      if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
        GooglePlayServicesUtil.getErrorDialog(resultCode, this,
            PLAY_SERVICES_RESOLUTION_REQUEST).show();
      } else {
        Log.i(TAG, "This device is not supported.");
        finish();
      }
      return false;
    }

    return true;
  }
}
