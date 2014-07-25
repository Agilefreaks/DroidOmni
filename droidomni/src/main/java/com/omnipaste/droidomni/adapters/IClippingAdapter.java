package com.omnipaste.droidomni.adapters;

import android.widget.ListAdapter;

import com.omnipaste.omnicommon.dto.ClippingDto;

public interface IClippingAdapter extends ListAdapter {
  public void add(ClippingDto clippingDto);
}
