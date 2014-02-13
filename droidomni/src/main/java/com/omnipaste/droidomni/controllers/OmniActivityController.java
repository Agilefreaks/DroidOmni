package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.activities.OmniActivity;

public interface OmniActivityController {
  public void run(OmniActivity omniActivity, Bundle savedInstance);

  void stop();
}
