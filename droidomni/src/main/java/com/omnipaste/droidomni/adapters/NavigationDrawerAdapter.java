package com.omnipaste.droidomni.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.views.NavigationDrawerView;
import com.omnipaste.droidomni.views.NavigationDrawerView_;

import java.util.ArrayList;
import java.util.Arrays;

public class NavigationDrawerAdapter extends LocalAdapter<NavigationDrawerItem, NavigationDrawerView> {
  public static NavigationDrawerAdapter build(Resources resources) {
    return new NavigationDrawerAdapter(new ArrayList<>(
        Arrays.asList(
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_clippings), NavigationMenu.Clippings, R.drawable.ic_clippings, true),
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_sign_out), NavigationMenu.SignOut, R.drawable.ic_logout)))
    );
  }

  private NavigationDrawerAdapter(ArrayList<NavigationDrawerItem> navigationDrawerItems) {
    items = navigationDrawerItems;
  }

  @Override
  protected View buildView(Context context) {
    return NavigationDrawerView_.build(context);
  }
}
