package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.view.View;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.fragment.NavigationDrawerFragment;

import javax.inject.Inject;

public class OmniActivityControllerImpl implements OmniActivityController {

  @Inject
  public SessionService sessionService;

  public OmniActivityControllerImpl() {
  }

  @Override
  public void run(Bundle savedInstance) {
    if (savedInstance == null) {
      setInitialFragment();
    }
  }

  @Override
  public void stop() {

  }

  @Override
  public void setUpNavigationDrawer(NavigationDrawerFragment navigationDrawer) {
//    navigationDrawer.setUp(actionBarController.setupNavigationDrawer(activity.drawerLayout, this));
  }

  public void onDrawerOpened(View drawerView) {
  }

  public void onDrawerClosed(View drawerView) {
  }

  private void setInitialFragment() {
  }
}
