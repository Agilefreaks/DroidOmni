package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.events.DeviceInitEvent;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.fragments.ClippingsFragment_;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.fragments.MainFragment_;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class MainController implements MainActivityController {
  private EventBus eventBus = EventBus.getDefault();
  private ConfigurationService configurationService;
  private MainActivity activity;

  @Inject
  public MainController(ConfigurationService configurationService) {
    this.configurationService = configurationService;
  }

  @Override
  public void run(MainActivity mainActivity, Bundle savedInstanceState) {
    eventBus.register(this);

    this.activity = mainActivity;

    if (savedInstanceState == null) {
      Configuration configuration = configurationService.getConfiguration();
      Fragment fragmentToDisplay;

      if (configuration.hasChannel()) {
        fragmentToDisplay = MainFragment_.builder().build();
        setSubtitle(configuration.getChannel());
      } else {
        fragmentToDisplay = LoginFragment_.builder().build();
      }

      mainActivity.setFragment(fragmentToDisplay);
    }
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(DeviceInitEvent event) {
    activity.setFragment(ClippingsFragment_.builder().build());
    setTitle(R.string.clippings_title);
  }

  private void setTitle(int clippings_title) {
    getActionBar().setTitle(clippings_title);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(LoginEvent event) {
    activity.setFragment(MainFragment_.builder().build());
  }

  public void setSubtitle(String subtitle) {
    getActionBar().setSubtitle(subtitle);
  }

  private ActionBar getActionBar() {
    ActionBar actionBar = activity.getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(true);
//    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    return actionBar;
  }
}
