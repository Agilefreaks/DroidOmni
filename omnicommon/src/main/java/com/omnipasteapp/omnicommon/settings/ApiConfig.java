package com.omnipasteapp.omnicommon.settings;

public class ApiConfig {
  public static String BaseUrlKey = "apiUrl";

  private String baseUrl;
  private Resources resources;

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public Resources getResources() {
    return resources;
  }

  public void setResources(Resources resources) {
    this.resources = resources;
  }
}
