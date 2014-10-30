package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.activities.LauncherActivity;

public interface MainActivityController {
  public void run(LauncherActivity launcherActivity, Bundle savedInstance);

  void stop();
}
