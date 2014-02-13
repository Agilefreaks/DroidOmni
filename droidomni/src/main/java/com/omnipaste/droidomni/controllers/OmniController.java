package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.events.NavigationItemClicked;
import com.omnipaste.droidomni.fragments.ClippingsFragment_;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.util.functions.Action0;

public class OmniController implements OmniActivityController {
  private EventBus eventBus = EventBus.getDefault();
  private OmniActivity activity;
  private ConfigurationService configurationService;

  @Inject
  public OmniController(ConfigurationService configurationService) {
    this.configurationService = configurationService;
  }

  @Override
  public void run(OmniActivity omniActivity, Bundle savedInstance) {
    eventBus.register(this);
    activity = omniActivity;

    if (savedInstance == null) {
      setInitialFragment();
    }
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(NavigationItemClicked event) {
    if (event.getNavigationDrawerItem().getNavigationMenu() == NavigationMenu.SignOut) {
      OmniService.stop(activity).
          subscribeOn(Schedulers.io()).
          observeOn(AndroidSchedulers.mainThread()).
          doOnCompleted(new Action0() {
            @Override
            public void call() {
              // remove channel
              Configuration configuration = configurationService.getConfiguration();
              configuration.setChannel(null);
              configurationService.setConfiguration(configuration);

              activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity_.class));
              activity.finish();
            }
          }).subscribe();
    }
  }

  private void setTitle(int title) {
    getActionBar().setTitle(title);
  }

  private void setSubtitle(String subtitle) {
    getActionBar().setSubtitle(subtitle);
  }

  private ActionBar getActionBar() {
    ActionBar actionBar = activity.getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    return actionBar;
  }

  private void setInitialFragment() {
    setFragment(ClippingsFragment_.builder().build());
    setTitle(R.string.clippings_title);
    setSubtitle(configurationService.getConfiguration().getChannel());
  }

  private void setFragment(Fragment fragment) {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.omni_container, fragment)
        .commitAllowingStateLoss();

    supportFragmentManager.executePendingTransactions();
  }
}
