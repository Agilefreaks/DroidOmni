package com.omnipaste.droidomni.domain;

public class AboutItem {
  private String title;
  private String subtitle;

  public AboutItem(String title, String subtitle) {
    this.title = title;
    this.subtitle = subtitle;
  }

  public String getTitle() {
    return title;
  }

  public String getSubtitle() {
    return subtitle;
  }
}
