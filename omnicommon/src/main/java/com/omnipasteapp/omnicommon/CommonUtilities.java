package com.omnipasteapp.omnicommon;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public final class CommonUtilities {
  public static int getAppVersion(Context context) {
    PackageInfo packageInfo = new PackageInfo();

    try {
      if (context.getPackageManager() != null) {
        packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      }
    } catch (PackageManager.NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Could not get package name: " + e);
    }

    return packageInfo.versionCode;
  }
}
