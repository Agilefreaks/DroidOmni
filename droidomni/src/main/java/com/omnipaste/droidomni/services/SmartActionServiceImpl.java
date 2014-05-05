package com.omnipaste.droidomni.services;

import android.app.PendingIntent;
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
  public void run(ClippingDto clippingDto) {
    context.startActivity(buildIntent(clippingDto));
  }

  @Override
  public CharSequence getTitle(ClippingDto clippingDto) {
    return context.getText(getSmartAction(clippingDto).getTitle());
  }

  @Override
  public int getIcon(ClippingDto clippingDto) {
    return getSmartAction(clippingDto).getIcon();
  }

  @Override
  public PendingIntent buildPendingIntent(ClippingDto clippingDto) {
    return PendingIntent.getActivity(context, 0, buildIntent(clippingDto), 0);
  }

  private Intent buildIntent(ClippingDto clippingDto) {
    return getSmartAction(clippingDto).buildIntent(clippingDto);
  }

  private SmartAction getSmartAction(ClippingDto clippingDto) {
    return smartActions.get(clippingDto.getType());
  }
}
