package com.omnipaste.droidomni.events;

import com.omnipaste.droidomni.domain.NavigationDrawerItem;

public class NavigationItemClicked {
  private NavigationDrawerItem navigationDrawerItem;

  public NavigationItemClicked(NavigationDrawerItem navigationDrawerItem) {
    this.navigationDrawerItem = navigationDrawerItem;
  }

  public NavigationDrawerItem getNavigationDrawerItem() {
    return navigationDrawerItem;
  }
}
