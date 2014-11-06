package com.omnipaste.droidomni.service.smartaction;

import android.content.Intent;
import android.net.Uri;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

public class SmartActionAddress extends SmartAction {
  @Override
  public int getTitle() {
    return R.string.smart_action_navigate;
  }

  @Override
  public int[] getIcon() {
    return new int[] { R.drawable.ic_smart_action_address_light, R.drawable.ic_smart_action_address } ;
  }

  @Override
  protected String getAction() {
    return Intent.ACTION_VIEW;
  }

  @Override
  protected Uri getURI(ClippingDto clippingDto) {
    return Uri.parse("google.navigation:q=" + clippingDto.getContent());
  }
}
