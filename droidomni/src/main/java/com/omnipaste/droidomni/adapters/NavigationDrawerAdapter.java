package com.omnipaste.droidomni.adapters;

import android.content.Context;
import android.view.View;

import com.omnipaste.droidomni.views.NavigationDrawerView;
import com.omnipaste.droidomni.views.NavigationDrawerView_;

public class NavigationDrawerAdapter extends LocalAdapter<NavigationDrawerItem, NavigationDrawerView> {
  public NavigationDrawerAdapter() {
    super();

    items.add(new NavigationDrawerItem("Sign out", false));
    items.add(new NavigationDrawerItem("Clippings", true));
  }

  @Override
  protected View buildView(Context context) {
    return NavigationDrawerView_.build(context);
  }
}
