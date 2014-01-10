package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Devices;

public class OmniApi {
  private final String baseUrl;
  private Devices devices;

  public OmniApi(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public Devices devices() {
    return devices == null ? devices = new Devices(baseUrl) : devices;
  }
}
