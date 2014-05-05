package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;
import android.net.Uri;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

public class PhoneNumberSmartAction extends SmartAction {
  @Override
  public int getTitle() {
    return R.string.smart_action_call;
  }

  @Override
  public int getIcon() {
    return R.drawable.ic_smart_action_phone_number_light;
  }

  @Override
  protected String getAction() {
    return Intent.ACTION_CALL;
  }

  @Override
  protected Uri getURI(ClippingDto clippingDto) {
    return Uri.parse("tel:" + clippingDto.getContent());
  }
}
