package com.omnipaste.droidomni.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(android.R.layout.simple_list_item_1)
public class ClippingView extends LinearLayout implements HasSetup<ClippingDto> {
  @ViewById
  public TextView text1;

  public ClippingView(Context context) {
    super(context);
  }

  public void setUp(ClippingDto clippingDto) {
    text1.setText(clippingDto.getContent());
  }
}
