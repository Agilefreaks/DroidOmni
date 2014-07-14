package com.omnipaste.droidomni.services;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;

public interface FragmentService {
  void setFragment(FragmentActivity fragmentActivity, int container, Fragment fragment);
}
