package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.activities.LoginActivity;

public interface LoginActivityController {
  void start(LoginActivity loginActivity, Bundle savedInstance);

  void stop();
}
