package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;
import android.net.Uri;

import com.omnipaste.omnicommon.dto.ClippingDto;

public class PhoneNumberSmartAction extends SmartAction {
  @Override
  protected String getAction() {
    return Intent.ACTION_CALL;
  }

  @Override
  protected Uri getURI(ClippingDto clippingDto) {
    return Uri.parse("tel:" + clippingDto.getContent());
  }
}
