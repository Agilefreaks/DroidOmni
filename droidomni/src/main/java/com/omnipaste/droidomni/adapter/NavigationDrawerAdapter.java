package com.omnipaste.droidomni.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.ui.view.NavigationDrawerItemView;
import com.omnipaste.droidomni.ui.view.NavigationDrawerItemView_;

import java.util.ArrayList;
import java.util.Arrays;

public class NavigationDrawerAdapter extends LocalAdapter<NavigationDrawerItem, NavigationDrawerItemView> {
  public static NavigationDrawerAdapter build(Resources resources) {
    return new NavigationDrawerAdapter(new ArrayList<>(
        Arrays.asList(
            new NavigationDrawerItem(resources.getString(R.string.navigation_drawer_activity), NavigationMenu.ACTIVITY, R.drawable.ic_activity, true))
    ));
  }

  private NavigationDrawerAdapter(ArrayList<NavigationDrawerItem> navigationDrawerItems) {
    items = navigationDrawerItems;
  }

  @Override
  protected View buildView(Context context) {
    return NavigationDrawerItemView_.build(context);
  }
}
