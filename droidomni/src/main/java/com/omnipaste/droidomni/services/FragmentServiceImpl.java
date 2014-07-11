package com.omnipaste.droidomni.services;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class FragmentServiceImpl implements FragmentService {
  @Override
  public void setFragment(FragmentActivity fragmentActivity, int container, Fragment fragment) {
    FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
    supportFragmentManager
        .beginTransaction()
        .add(container, fragment)
        .commit();
  }
}
