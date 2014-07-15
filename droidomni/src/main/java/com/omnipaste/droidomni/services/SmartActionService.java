package com.omnipaste.droidomni.services;

import android.app.PendingIntent;

import com.omnipaste.droidomni.services.smartaction.SmartAction;
import com.omnipaste.droidomni.services.smartaction.SmartActionAddress;
import com.omnipaste.droidomni.services.smartaction.SmartActionPhoneNumber;
import com.omnipaste.droidomni.services.smartaction.SmartActionWebSite;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.HashMap;

public interface SmartActionService {
  public static final HashMap<ClippingDto.ClippingType, SmartAction> SMART_ACTIONS = new HashMap<ClippingDto.ClippingType, SmartAction>() {{
    put(ClippingDto.ClippingType.phoneNumber, new SmartActionPhoneNumber());
    put(ClippingDto.ClippingType.url, new SmartActionWebSite());
    put(ClippingDto.ClippingType.address, new SmartActionAddress());
  }};

  void run(ClippingDto clippingDto);

  public CharSequence getTitle(ClippingDto clippingDto);

  public int getIcon(ClippingDto clippingDto);

  public PendingIntent buildPendingIntent(ClippingDto clippingDto);
}
