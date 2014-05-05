package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;
import android.net.Uri;

import com.omnipaste.omnicommon.dto.ClippingDto;

public abstract class SmartAction {
  public Intent buildIntent(ClippingDto clippingDto) {
    Intent result = new Intent(getAction(), getURI(clippingDto));
    result.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    return result;
  }

  public abstract int getTitle();

  public abstract int[] getIcon();

  protected abstract String getAction();

  protected abstract Uri getURI(ClippingDto clippingDto);
}
