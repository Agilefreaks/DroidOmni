package com.omnipaste.droidomni.adapters;

import android.content.Context;
import android.view.View;

import com.omnipaste.droidomni.adapter.LocalAdapter;
import com.omnipaste.droidomni.views.AboutItemView;
import com.omnipaste.droidomni.views.AboutItemView_;

public class AboutAdapter extends LocalAdapter<AboutItem, AboutItemView> {
  @Override
  protected View buildView(Context context) {
    return AboutItemView_.build(context);
  }
}
