package com.omnipaste.droidomni.fragments.clippings;

import android.support.v4.app.Fragment;

public class ClippingsListFactory implements IClippingsListFactory {
  private static final int LOCAL_POSITION = 1;
  private static final int CLOUD_POSITION = 2;

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public Fragment getFragment(int position) {
    switch (position) {
      case LOCAL_POSITION:
        return LocalFragment_.builder().build();
      case CLOUD_POSITION:
        return CloudFragment_.builder().build();
      default:
        return AllFragment_.builder().build();
    }
  }
}
