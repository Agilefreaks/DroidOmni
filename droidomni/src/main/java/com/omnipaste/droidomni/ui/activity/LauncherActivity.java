package com.omnipaste.droidomni.ui.activity;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.LauncherPresenter;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_launcher)
public class LauncherActivity extends BaseActivity<LauncherPresenter> implements LauncherPresenter.View {
  private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

  @Inject public LauncherPresenter presenter;

  @Override protected LauncherPresenter getPresenter() {
    return presenter;
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

  @Override public void showPlayServiceErrorDialog(int status) {
    GooglePlayServicesUtil.getErrorDialog(status, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
  }
}
