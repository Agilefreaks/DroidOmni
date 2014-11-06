package com.omnipaste.droidomni.service.smartaction;

import android.content.Intent;
import android.net.Uri;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

public class SmartActionWebSite extends SmartAction {
  @Override
  public int getTitle() {
    return R.string.smart_action_browse;
  }

  @Override
  public int[] getIcon() {
    return new int[] { R.drawable.ic_smart_action_uri_light, R.drawable.ic_smart_action_uri };
  }

  @Override
  protected String getAction() {
    return Intent.ACTION_VIEW;
  }

  @Override
  protected Uri getURI(ClippingDto clippingDto) {
    return Uri.parse(clippingDto.getContent());
  }
}
