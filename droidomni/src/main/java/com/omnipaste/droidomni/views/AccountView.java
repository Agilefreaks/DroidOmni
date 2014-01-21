package com.omnipaste.droidomni.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(android.R.layout.simple_list_item_1)
public class AccountView extends LinearLayout {
  @ViewById
  public TextView text1;

  public AccountView(Context context) {
    super(context);
  }

  public void fillData(String name) {
    text1.setText(name);
  }
}
