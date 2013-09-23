package com.omnipasteapp.omniclipboard.api.builders;

import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.Sender;

import java.io.BufferedReader;

public class ClippingBuilder extends JsonBuilder<Clipping> {
  @Override
  public Class<Clipping> getType() {
    return Clipping.class;
  }

  @Override
  public Clipping build(BufferedReader in) {
    Clipping clipping = super.build(in);

    clipping.setSender(Sender.Omni);

    return clipping;
  }
}
