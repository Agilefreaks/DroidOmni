package com.omnipaste.droidomni.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.controllers.LoginActivityController;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {

  @Inject
  public LoginActivityController controller;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DroidOmniApplication.inject(this);

    controller.start(this, savedInstanceState);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    controller.stop();
  }
}
