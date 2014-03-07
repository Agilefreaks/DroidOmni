package com.omnipaste.droidomni.actionbar;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

public abstract class SimpleTabListener extends ViewPager.SimpleOnPageChangeListener implements ActionBar.TabListener {
  public abstract void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction);

  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
  }

  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
  }
}
