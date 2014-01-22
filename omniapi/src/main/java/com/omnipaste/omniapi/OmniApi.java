package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Devices;

class OmniApi implements IOmniApi {
  private final String baseUrl;
  private Devices devices;

  OmniApi(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public Devices devices() {
    return devices == null ? devices = new Devices(baseUrl) : devices;
  }
}
