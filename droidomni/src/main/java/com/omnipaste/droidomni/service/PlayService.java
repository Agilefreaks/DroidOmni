package com.omnipaste.droidomni.service;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.omnipaste.droidomni.R;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayService {
  private Context context;

  @Inject
  public PlayService(Context context) {
    this.context = context;
  }

  public int status() {
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
    if (status != ConnectionResult.SUCCESS) {
      if (!GooglePlayServicesUtil.isUserRecoverableError(status)) {
        Toast.makeText(context, R.string.main_activity_device_not_supported, Toast.LENGTH_LONG).show();
      }
      return status;
    }
    return status;
  }
}
