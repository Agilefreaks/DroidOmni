package com.omnipaste.droidomni.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.omnipaste.droidomni.views.HasSetup;

import java.util.ArrayList;

public abstract class LocalAdapter<T, TView extends HasSetup<T>> extends BaseAdapter {
  protected ArrayList<T> items;

  protected LocalAdapter() {
    items = new ArrayList<>();
  }

  @Override
  public int getCount() {
    return items.size();
  }

  @Override
  public T getItem(int position) {
    return items.get(this.getCount() - position - 1);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @SuppressWarnings("unchecked")
  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    View itemView;
    T item = this.getItem(position);

    if (view != null) {
      itemView = view;
    }
    else {
      itemView = buildView(viewGroup.getContext());
    }

    ((TView) itemView).setUp(item);

    return itemView;
  }

  protected abstract View buildView(Context context);
}
