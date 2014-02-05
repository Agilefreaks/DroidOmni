package com.omnipaste.droidomni.adapters;

import android.content.Context;
import android.view.View;

import com.omnipaste.droidomni.views.ClippingView;
import com.omnipaste.droidomni.views.ClippingView_;
import com.omnipaste.omnicommon.dto.ClippingDto;

public class ClippingAdapter extends LocalAdapter<ClippingDto, ClippingView> {
  @Override
  protected View buildView(Context context) {
    return ClippingView_.build(context);
  }

  @Override
  public ClippingDto getItem(int position) {
    return items.get(this.getCount() - position - 1);
  }

  public void add(ClippingDto clippingDto) {
    items.add(clippingDto);
    this.notifyDataSetChanged();
  }
}
