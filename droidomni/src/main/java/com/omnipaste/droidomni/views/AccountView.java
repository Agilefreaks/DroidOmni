package com.omnipaste.droidomni.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountView extends LinearLayout {
  private TextView text;

  public AccountView(Context context) {
    super(context);
    init();
  }

  public void fillData(String name) {
    text.setText(name);
  }

  private void init() {
    inflate(getContext(), android.R.layout.simple_list_item_1, this);
    text = (TextView) findViewById(android.R.id.text1);
  }
}
