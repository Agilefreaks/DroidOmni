package com.omnipaste.droidomni.ui.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.NavigationDrawerItem;
import com.omnipaste.droidomni.views.HasSetup;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_secondary_navigation_drawer_item)
public class SecondaryNavigationDrawerItemView extends LinearLayout implements HasSetup<NavigationDrawerItem> {
  @ViewById
  public TextView textTitle;

  public SecondaryNavigationDrawerItemView(Context context) {
    super(context);
  }

  @Override
  public void setUp(NavigationDrawerItem item) {
    textTitle.setText(item.getTitle());
    textTitle.setSelected(item.getIsSelected());
  }
}
