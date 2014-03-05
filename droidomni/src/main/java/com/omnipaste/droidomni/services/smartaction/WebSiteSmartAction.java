package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;
import android.net.Uri;

import com.omnipaste.omnicommon.dto.ClippingDto;

public class WebSiteSmartAction extends SmartAction {
  @Override
  protected String getAction() {
    return Intent.ACTION_VIEW;
  }

  @Override
  protected Uri getURI(ClippingDto clippingDto) {
    return Uri.parse(clippingDto.getContent());
  }
}
