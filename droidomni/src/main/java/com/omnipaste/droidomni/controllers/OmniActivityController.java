package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.ui.fragment.NavigationDrawerFragment;

public interface OmniActivityController {
  public void run(Bundle savedInstance);

  void stop();

  void setUpNavigationDrawer(NavigationDrawerFragment navigationDrawer);
}
