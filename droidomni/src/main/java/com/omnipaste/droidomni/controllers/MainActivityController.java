package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.activities.MainActivity;

public interface MainActivityController {
  public void run(MainActivity mainActivity, Bundle savedInstance);

  void stop();
}
