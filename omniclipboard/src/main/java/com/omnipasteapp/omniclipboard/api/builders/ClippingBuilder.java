package com.omnipasteapp.omniclipboard.api.builders;

import com.omnipasteapp.omnicommon.models.Clipping;

public class ClippingBuilder extends JsonBuilder<Clipping> {
  @Override
  public Class<Clipping> getType() {
    return Clipping.class;
  }
}
