package com.omnipaste.droidomni.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.omnipaste.droidomni.ui.view.HasSetup;
import com.omnipaste.droidomni.ui.view.clipping.LocalClippingView_;
import com.omnipaste.droidomni.ui.view.clipping.OmniClippingView_;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.ArrayList;

public class ClippingAdapter extends RecyclerView.Adapter<ClippingAdapter.ViewHolder> {
  public final int LIST_SIZE = 42;

  public static ClippingAdapter build() {
    return new ClippingAdapter(new ArrayList<ClippingDto>());
  }

  private ArrayList<ClippingDto> items;

  private ClippingAdapter(ArrayList<ClippingDto> items) {
    this.items = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    ViewHolder viewHolder;

    if (viewType == 1) {
      viewHolder = new ViewHolder(OmniClippingView_.build(viewGroup.getContext()));
    }
    else {
      viewHolder = new ViewHolder(LocalClippingView_.build(viewGroup.getContext()));
    }

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int index) {
    ClippingDto clippingDto = items.get(index);
    viewHolder.setUp(clippingDto);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  @Override
  public int getItemViewType(int position) {
    ClippingDto clippingDto = items.get(position);
    return clippingDto.getClippingProvider() == ClippingDto.ClippingProvider.CLOUD ? 1 : 0;
  }

  public void add(ClippingDto clippingDto) {
    items.add(0, clippingDto);

    if (items.size() >= LIST_SIZE + 10) {
      items.subList(LIST_SIZE, items.size()).clear();
    }

    notifyItemInserted(0);
  }

  public void remove(ClippingDto clippingDto) {
    int index = items.indexOf(clippingDto);
    items.remove(clippingDto);

    notifyItemRemoved(index);
  }

  public int getCount() {
    return items.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }

    @SuppressWarnings("unchecked")
    public void setUp(ClippingDto clippingDto) {
      ((HasSetup<ClippingDto>) itemView).setUp(clippingDto);
    }
  }
}
