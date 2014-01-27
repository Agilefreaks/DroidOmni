package com.omnipaste.droidomni.events;

import android.support.v4.app.Fragment;

public class FragmentChanged {
  private Fragment fragment;

  public FragmentChanged(Fragment fragment) {
    this.fragment = fragment;
  }

  public Fragment getFragment() {
    return fragment;
  }
}
