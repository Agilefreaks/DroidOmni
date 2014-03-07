package com.omnipaste.droidomni.controllers;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.actionbar.ActionBarDrawerToggleListener;

public class ActionBarControllerImpl implements ActionBarController {

  private ActionBarActivity actionBarActivity;
  private ActionBar actionBar;

  @Override
  public void run(ActionBarActivity actionBarActivity) {
    this.actionBarActivity = actionBarActivity;
  }

  @Override
  public void stop() {
  }

  @Override
  public void setTitle(int title) {
    getActionBar().setTitle(title);
  }

  @Override
  public void setSubtitle(String subtitle) {
    getActionBar().setSubtitle(subtitle);
  }

  @Override
  public void setNavigationMode(int navigationMode) {
    getActionBar().setNavigationMode(navigationMode);
  }

  @Override
  public void setSelectedNavigationItem(int position) {
    getActionBar().setSelectedNavigationItem(position);
  }

  @Override
  public ActionBarDrawerToggle setupNavigationDrawer(DrawerLayout drawerLayout, final ActionBarDrawerToggleListener listener) {
    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

    final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
        actionBarActivity,
        drawerLayout,
        R.drawable.ic_drawer,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    ) {
      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        listener.onDrawerClosed(drawerView);
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        listener.onDrawerOpened(drawerView);
      }
    };

    drawerLayout.post(new Runnable() {
      @Override
      public void run() {
        drawerToggle.syncState();
      }
    });

    drawerLayout.setDrawerListener(drawerToggle);

    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);

    return drawerToggle;
  }

  @Override
  public void addTab(int id, ActionBar.TabListener tabListener) {
    getActionBar().addTab(getActionBar().newTab().setText(id).setTabListener(tabListener));
  }

  private ActionBar getActionBar() {
    if (actionBar == null) {
      actionBar = actionBarActivity.getSupportActionBar();
      actionBar.setDisplayShowTitleEnabled(true);
    }

    return actionBar;
  }
}
