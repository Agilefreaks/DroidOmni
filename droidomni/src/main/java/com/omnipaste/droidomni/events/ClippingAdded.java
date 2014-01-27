package com.omnipaste.droidomni.events;

import com.omnipaste.omnicommon.dto.ClippingDto;

public class ClippingAdded {
  private ClippingDto clipping;

  public ClippingAdded(ClippingDto clipping) {
    this.clipping = clipping;
  }

  public ClippingDto getClipping() {
    return clipping;
  }
}
