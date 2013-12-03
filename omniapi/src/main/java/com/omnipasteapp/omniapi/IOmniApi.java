package com.omnipasteapp.omniapi;

import com.omnipasteapp.omniapi.resources.IClippings;
import com.omnipasteapp.omniapi.resources.IDevices;

public interface IOmniApi {
  IClippings clippings();

  IDevices devices();
}
