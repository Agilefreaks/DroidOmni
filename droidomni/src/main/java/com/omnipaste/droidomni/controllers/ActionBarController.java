package com.omnipaste.droidomni.controllers;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.omnipaste.droidomni.actionbar.ActionBarDrawerToggleListener;

public interface ActionBarController {
  void run(ActionBarActivity actionBarActivity);

  void stop();

  public void setTitle(int title);

  public void setSubtitle(String subtitle);

  ActionBarDrawerToggle setupNavigationDrawer(DrawerLayout drawerLayout, final ActionBarDrawerToggleListener listener);
}
