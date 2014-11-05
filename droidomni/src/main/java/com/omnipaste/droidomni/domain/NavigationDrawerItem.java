package com.omnipaste.droidomni.domain;

import com.omnipaste.droidomni.NavigationMenu;

public class NavigationDrawerItem {
  private String title;
  private NavigationMenu navigationMenu;
  private Boolean isSelected = false;
  private Integer icon;

  public NavigationDrawerItem(String title, NavigationMenu navigationMenu)
  {
    this.title = title;
    this.navigationMenu = navigationMenu;
  }

  public NavigationDrawerItem(String title, NavigationMenu navigationMenu, Boolean isSelected)
  {
    this(title, navigationMenu);

    this.isSelected = isSelected;
  }

  public NavigationDrawerItem(String title, NavigationMenu navigationMenu, Integer icon) {
    this(title, navigationMenu);

    this.icon = icon;
  }

  public NavigationDrawerItem(String title, NavigationMenu navigationMenu, Integer icon, Boolean isSelected) {
    this(title, navigationMenu, icon);

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

  public Integer getIcon() {
    return icon;
  }

  public void setIcon(Integer icon) {
    this.icon = icon;
  }
}
