package com.omnipaste.droidomni;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.droidomni.services.SessionService;

public class Helpers {
  public static void setFragment(FragmentActivity fragmentActivity, int container, Fragment fragment) {
    FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();

    supportFragmentManager
        .beginTransaction()
        .replace(container, fragment)
        .commitAllowingStateLoss();

    supportFragmentManager.executePendingTransactions();
  }

  public static void signOut(Activity activity, SessionService sessionService) {
    OmniService.stop(activity);
    sessionService.logout();
  }
}
