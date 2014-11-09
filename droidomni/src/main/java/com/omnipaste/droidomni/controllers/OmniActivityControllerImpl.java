package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.view.View;

import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.fragment.NavigationDrawerFragment;

import javax.inject.Inject;

public class OmniActivityControllerImpl implements OmniActivityController {
  private OmniActivity activity;

  @Inject
  public SessionService sessionService;

  public OmniActivityControllerImpl() {
  }

  @Override
  public void run(OmniActivity omniActivity, Bundle savedInstance) {
    activity = omniActivity;

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
    if (!activity.navigationDrawer.isAdded()) {
      return;
    }

    activity.supportInvalidateOptionsMenu();
  }

  public void onDrawerClosed(View drawerView) {
    if (!activity.navigationDrawer.isAdded()) {
      return;
    }

    activity.supportInvalidateOptionsMenu();
  }

  private void setInitialFragment() {
  }
}
