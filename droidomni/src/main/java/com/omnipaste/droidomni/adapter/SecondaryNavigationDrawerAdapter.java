package com.omnipaste.droidomni.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.ui.view.SecondaryNavigationDrawerItemView;
import com.omnipaste.droidomni.ui.view.SecondaryNavigationDrawerItemView_;

import java.util.ArrayList;
import java.util.Arrays;

public class SecondaryNavigationDrawerAdapter extends LocalAdapter<NavigationDrawerItem, SecondaryNavigationDrawerItemView> {
  public static SecondaryNavigationDrawerAdapter build(Resources resources) {
    return new SecondaryNavigationDrawerAdapter(new ArrayList<>(
        Arrays.asList(
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_settings), NavigationMenu.SETTINGS),
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_about), NavigationMenu.ABOUT),
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_privacy_policy), NavigationMenu.PRIVACY_POLICY))
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
