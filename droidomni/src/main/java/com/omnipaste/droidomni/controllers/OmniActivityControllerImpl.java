package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.actionbar.ActionBarDrawerToggleListener;
import com.omnipaste.droidomni.activities.AboutActivity_;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.activities.SettingsActivity;
import com.omnipaste.droidomni.events.NavigationItemClicked;
import com.omnipaste.droidomni.events.SignOutEvent;
import com.omnipaste.droidomni.fragments.NavigationDrawerFragment;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment_;
import com.omnipaste.droidomni.services.FragmentService;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.droidomni.services.SessionService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class OmniActivityControllerImpl implements OmniActivityController, ActionBarDrawerToggleListener {
  private EventBus eventBus = EventBus.getDefault();
  private OmniActivity activity;

  @Inject
  public ActionBarController actionBarController;

  @Inject
  public FragmentService fragmentService;

  @Inject
  public SessionService sessionService;

  public OmniActivityControllerImpl() {
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
    Intent intent = null;

    switch (event.getNavigationDrawerItem().getNavigationMenu()) {
      case Clippings:
        break;
      case Settings:
        activity.drawerLayout.closeDrawers();
        intent = new Intent(activity, SettingsActivity.class);
        break;
      case About:
        activity.drawerLayout.closeDrawers();
        intent = new Intent(activity, AboutActivity_.class);
        break;
      case PrivacyPolicy:
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
    OmniService.stop(activity);
    sessionService.logout();

    activity.startActivity(MainActivity.getIntent(activity));
    activity.finish();
  }

  private void setInitialFragment() {
    fragmentService.setFragment(activity, R.id.omni_container, ClippingsFragment_.builder().build());
  }
}
