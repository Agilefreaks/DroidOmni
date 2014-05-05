package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;
import android.net.Uri;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

public class SmartActionPhoneNumber extends SmartAction {
  @Override
  public int getTitle() {
    return R.string.smart_action_call;
  }

  @Override
  public int[] getIcon() {
    return new int[] { R.drawable.ic_smart_action_phone_number_light, R.drawable.ic_smart_action_phone_number };
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
