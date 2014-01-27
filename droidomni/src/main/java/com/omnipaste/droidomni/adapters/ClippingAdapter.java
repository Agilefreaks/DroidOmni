package com.omnipaste.droidomni.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.omnipaste.droidomni.views.ClippingView;
import com.omnipaste.droidomni.views.ClippingView_;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.ArrayList;

public class ClippingAdapter extends BaseAdapter {
  private ArrayList<ClippingDto> clippings;

  public ClippingAdapter() {
    this.clippings = new ArrayList<>();
  }

  @Override
  public int getCount() {
    return clippings.size();
  }

  @Override
  public Object getItem(int position) {
    return clippings.get(this.getCount() - position - 1);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    View clippingView;
    ClippingDto clipping = (ClippingDto) this.getItem(position);

    if (view != null) {
      clippingView = view;
    }
    else {
      clippingView = ClippingView_.build(viewGroup.getContext());
    }

    ((ClippingView) clippingView).fillData(clipping.getContent());
    return clippingView;
  }

  public void add(ClippingDto clippingDto) {
    clippings.add(clippingDto);
    this.notifyDataSetChanged();
  }
}
