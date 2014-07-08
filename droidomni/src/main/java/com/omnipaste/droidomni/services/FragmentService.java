package com.omnipaste.droidomni.services;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public interface FragmentService {
  void setFragment(FragmentActivity fragmentActivity, int container, Fragment fragment);
}
