package com.omnipaste.droidomni.domain;

import com.omnipaste.omnicommon.dto.ClippingDto;

public class Clipping extends Command<ClippingDto> {
  public static Clipping add(ClippingDto clippingDto) {
    return new Clipping(clippingDto, Action.ADD);
  }

  public static Clipping remove(ClippingDto clippingDto) {
    return new Clipping(clippingDto, Action.REMOVE);
  }

  private Clipping(ClippingDto clippingDto, Action action) {
    super(clippingDto, action);
  }
}
