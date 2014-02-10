package com.omnipaste.droidomni.adapters;

import com.omnipaste.droidomni.NavigationMenu;

public class NavigationDrawerItem {
  private String title;
  private NavigationMenu navigationMenu;
  private Boolean isSelected = false;

  public NavigationDrawerItem(String title, NavigationMenu navigationMenu) {
    this.title = title;
    this.navigationMenu = navigationMenu;
  }

  public NavigationDrawerItem(String title, NavigationMenu navigationMenu, Boolean isSelected) {
    this(title, navigationMenu);
    this.isSelected = isSelected;
  }

  public String getTitle() {
    return title;
  }

  public Boolean getIsSelected() {
    return isSelected;
  }

  public NavigationMenu getNavigationMenu() {
    return navigationMenu;
  }
}
