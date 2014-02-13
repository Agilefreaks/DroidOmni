package com.omnipaste.droidomni;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class Helpers {
  public static void setFragment(FragmentActivity fragmentActivity, int container, Fragment fragment) {
    FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();

    supportFragmentManager
        .beginTransaction()
        .replace(container, fragment)
        .commitAllowingStateLoss();

    supportFragmentManager.executePendingTransactions();
  }
}
