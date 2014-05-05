package com.omnipaste.droidomni.services;

import android.app.PendingIntent;

import com.omnipaste.omnicommon.dto.ClippingDto;

public interface SmartActionService {
  void run(ClippingDto clippingDto);

  public CharSequence getTitle(ClippingDto clippingDto);

  public int getIcon(ClippingDto clippingDto);

  public PendingIntent buildPendingIntent(ClippingDto clippingDto);
}
