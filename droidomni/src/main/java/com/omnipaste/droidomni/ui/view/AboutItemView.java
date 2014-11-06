package com.omnipaste.droidomni.ui.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.domain.AboutItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(android.R.layout.simple_list_item_2)
public class AboutItemView extends LinearLayout implements HasSetup<AboutItem> {
  @ViewById
  public TextView text1;

  @ViewById
  public TextView text2;

  public AboutItemView(Context context) {
    super(context);
  }

  @Override
  public void setUp(AboutItem item) {
    text1.setText(item.getTitle());
    text2.setText(item.getSubtitle());
  }
}
