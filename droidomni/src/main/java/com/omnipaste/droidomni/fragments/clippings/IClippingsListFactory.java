package com.omnipaste.droidomni.fragments.clippings;

import android.support.v4.app.Fragment;

public interface IClippingsListFactory {
  int getCount();

  Fragment getFragment(int position);
}
