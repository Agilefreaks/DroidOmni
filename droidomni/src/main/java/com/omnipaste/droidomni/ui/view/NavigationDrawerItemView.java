package com.omnipaste.droidomni.ui.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_navigation_drawer_item)
public class NavigationDrawerItemView extends LinearLayout implements HasSetup<NavigationDrawerItem> {
  @ViewById
  public TextView textTitle;

  public NavigationDrawerItemView(Context context) {
    super(context);
  }

  @Override
  public void setUp(NavigationDrawerItem item) {
    textTitle.setText(item.getTitle());
    textTitle.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
    textTitle.setSelected(item.getIsSelected());
  }
}
