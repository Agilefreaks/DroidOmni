package com.omnipaste.droidomni.services;

import android.content.Intent;

import com.omnipaste.omnicommon.dto.ClippingDto;

public interface SmartActionService {
  public Intent buildIntent(ClippingDto clippingDto);

  void run(ClippingDto clippingDto);
}
