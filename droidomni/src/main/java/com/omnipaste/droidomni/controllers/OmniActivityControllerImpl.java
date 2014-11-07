package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.omnipaste.droidomni.actionbar.ActionBarDrawerToggleListener;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.events.NavigationItemClicked;
import com.omnipaste.droidomni.events.SignOutEvent;
import com.omnipaste.droidomni.service.OmniService;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.fragment.NavigationDrawerFragment;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class OmniActivityControllerImpl implements OmniActivityController, ActionBarDrawerToggleListener {
  private EventBus eventBus = EventBus.getDefault();
  private OmniActivity activity;

  @Inject
  public SessionService sessionService;

  public OmniActivityControllerImpl() {
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

  @Override
  public void setUpNavigationDrawer(NavigationDrawerFragment navigationDrawer) {
//    navigationDrawer.setUp(actionBarController.setupNavigationDrawer(activity.drawerLayout, this));
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
    Intent intent = null;

    switch (event.getNavigationDrawerItem().getNavigationMenu()) {
      case ACTIVITY:
        break;
      case SETTINGS:
        activity.drawerLayout.closeDrawers();
        break;
      case ABOUT:
        activity.drawerLayout.closeDrawers();
        break;
      case PRIVACY_POLICY:
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(activity.tosUrl));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        break;
    }

    if (intent != null) {
      activity.startActivity(intent);
    }
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(SignOutEvent signOutEvent) {
    activity.stopService(OmniService.getIntent());
    sessionService.logout();
  }

  private void setInitialFragment() {
  }
}
