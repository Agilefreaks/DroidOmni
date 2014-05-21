package com.omnipaste.droidomni.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.views.SecondaryNavigationDrawerItemView;
import com.omnipaste.droidomni.views.SecondaryNavigationDrawerItemView_;

import java.util.ArrayList;
import java.util.Arrays;

public class SecondaryNavigationDrawerAdapter extends LocalAdapter<NavigationDrawerItem, SecondaryNavigationDrawerItemView> {
  public static SecondaryNavigationDrawerAdapter build(Resources resources) {
    return new SecondaryNavigationDrawerAdapter(new ArrayList<>(
        Arrays.asList(
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_settings), NavigationMenu.Settings),
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_about), NavigationMenu.About),
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_privacy_policy), NavigationMenu.PrivacyPolicy))
    ));
  }

  private SecondaryNavigationDrawerAdapter(ArrayList<NavigationDrawerItem> navigationDrawerItems) {
    items = navigationDrawerItems;
  }

  @Override
  protected View buildView(Context context) {
    return SecondaryNavigationDrawerItemView_.build(context);
  }
}
