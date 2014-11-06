package com.omnipaste.droidomni.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.AboutItem;
import com.omnipaste.droidomni.ui.view.AboutItemView;
import com.omnipaste.droidomni.ui.view.AboutItemView_;

import java.util.ArrayList;
import java.util.Arrays;

public class AboutAdapter extends LocalAdapter<AboutItem, AboutItemView> {
  public static AboutAdapter build(Resources resources, Context context) {
    return new AboutAdapter(new ArrayList<>(
        Arrays.asList(
            new AboutItem(resources.getString(R.string.app_name), resources.getString(R.string.motto)),
            new AboutItem(resources.getString(R.string.about_version), getVersionName(context))
        )
    ));
  }

  private static String getVersionName(Context context) {
    PackageInfo pInfo = new PackageInfo();
    try {
      pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    return pInfo.versionName;
  }

  private AboutAdapter(ArrayList<AboutItem> items) {
    this.items = items;
  }

  @Override
  protected View buildView(Context context) {
    return AboutItemView_.build(context);
  }
}
