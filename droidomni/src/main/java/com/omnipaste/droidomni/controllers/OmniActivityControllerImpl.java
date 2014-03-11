package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.Helpers;
import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.actionbar.ActionBarDrawerToggleListener;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.events.NavigationItemClicked;
import com.omnipaste.droidomni.fragments.NavigationDrawerFragment;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment_;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.droidomni.services.SessionService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class OmniActivityControllerImpl implements OmniActivityController, ActionBarDrawerToggleListener {
  private final SessionService sessionService;
  private EventBus eventBus = EventBus.getDefault();
  private OmniActivity activity;

  @Inject
  public ActionBarController actionBarController;

  @Inject
  public OmniActivityControllerImpl(SessionService sessionService) {
    DroidOmniApplication.inject(this);
    this.sessionService = sessionService;
  }

  @Override
  public void run(OmniActivity omniActivity, Bundle savedInstance) {
    eventBus.register(this);
    activity = omniActivity;

    actionBarController.run(omniActivity);

    if (savedInstance == null) {
      setInitialFragment();
    }
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
    actionBarController.stop();
  }

  @Override
  public void setUpNavigationDrawer(NavigationDrawerFragment navigationDrawer) {
    navigationDrawer.setUp(actionBarController.setupNavigationDrawer(activity.drawerLayout, this));
  }

  @Override
  public void onDrawerOpened(View drawerView) {
    if (!activity.navigationDrawer.isAdded()) {
      return;
    }

    activity.supportInvalidateOptionsMenu();
  }

  @Override
  public void onDrawerClosed(View drawerView) {
    if (!activity.navigationDrawer.isAdded()) {
      return;
    }

    activity.supportInvalidateOptionsMenu();
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(NavigationItemClicked event) {
    if (event.getNavigationDrawerItem().getNavigationMenu() == NavigationMenu.SignOut) {
      OmniService.stop(activity);

      sessionService.logout();
      activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity_.class));
      activity.finish();
    }
  }

  private void setInitialFragment() {
    ClippingsFragment clippingsFragment = ClippingsFragment_.builder().build();
    clippingsFragment.actionBarController = actionBarController;

    setFragment(clippingsFragment);

    actionBarController.setSubtitle(sessionService.getChannel());
  }

  private void setFragment(Fragment fragment) {
    Helpers.setFragment(activity, R.id.omni_container, fragment);
  }
}
