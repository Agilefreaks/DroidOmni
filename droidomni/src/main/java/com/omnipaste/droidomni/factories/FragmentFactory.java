package com.omnipaste.droidomni.factories;

import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.fragments.MainFragment_;
import com.omnipaste.omnicommon.domain.Configuration;

public class FragmentFactory {

  public static Fragment create_from(Configuration configuration) {
    return configuration.hasChannel() ? new MainFragment_() : new LoginFragment_();
  }
}
