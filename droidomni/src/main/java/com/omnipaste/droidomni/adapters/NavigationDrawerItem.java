package com.omnipaste.droidomni.adapters;

public class NavigationDrawerItem {
  private String title;
  private Boolean isSelected;

  public NavigationDrawerItem(String title) {
    this.title = title;
  }

  public NavigationDrawerItem(String title, Boolean isSelected) {
    this(title);
    this.isSelected = isSelected;
  }

  public String getTitle() {
    return title;
  }

  public Boolean getIsSelected() {
    return isSelected;
  }
}
