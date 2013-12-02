package com.omnipasteapp.omniapi.resources;

public abstract class Resource {
  private String _baseUrl;
  private String _version;
  private String _apiKey;

  public static <T extends Resource> T build(Class<T> clazz, String baseUrl, String version, String apiKey) {
    T result = null;

    try {
      result = clazz.newInstance();
      result.setBaseUrl(baseUrl);
      result.setVersion(version);
      result.setApiKey(apiKey);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return result;
  }

  public void setBaseUrl(String baseUrl) {
    _baseUrl = baseUrl;
  }

  public void setVersion(String version) {
    _version = version;
  }

  public void setApiKey(String apiKey) {
    _apiKey = apiKey;
  }

  public String getApiKey() {
    return _apiKey;
  }

  public String getUri() {
    return _baseUrl + "/" + _version + "/" + getUrl();
  }

  protected abstract String getUrl();
}
