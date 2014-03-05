package com.omnipaste.droidomni.services;

import android.content.Context;
import android.content.Intent;

import com.omnipaste.droidomni.services.smartaction.PhoneNumberSmartAction;
import com.omnipaste.droidomni.services.smartaction.SmartAction;
import com.omnipaste.droidomni.services.smartaction.WebSiteSmartAction;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.HashMap;

import javax.inject.Inject;

public class SmartActionServiceImpl implements SmartActionService {
  private Context context;

  public HashMap<ClippingDto.ClippingType, SmartAction> smartActions = new HashMap<ClippingDto.ClippingType, SmartAction>() {{
    put(ClippingDto.ClippingType.phoneNumber, new PhoneNumberSmartAction());
    put(ClippingDto.ClippingType.webSite, new WebSiteSmartAction());
  }};

  @Inject
  public SmartActionServiceImpl(Context context) {
    this.context = context;
  }

  @Override
  public Intent buildIntent(ClippingDto clippingDto) {
    return smartActions.get(clippingDto.getType()).buildIntent(clippingDto);
  }

  @Override
  public void run(ClippingDto clippingDto) {
    context.startActivity(buildIntent(clippingDto));
  }
}
