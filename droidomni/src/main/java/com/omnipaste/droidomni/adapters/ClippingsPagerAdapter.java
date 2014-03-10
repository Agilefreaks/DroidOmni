package com.omnipaste.droidomni.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

public class ClippingsPagerAdapter extends FragmentPagerAdapter {
  private ArrayList<ListFragment> fragments;

  public ClippingsPagerAdapter(FragmentManager fm) {
    super(fm);

    fragments = new ArrayList<>();
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return fragments.size();
  }

  public void addFragment(ListFragment fragment) {
    fragments.add(fragment);
    notifyDataSetChanged();
  }

  public ListFragment getFragment(int position) {
    return fragments.get(position);
  }
}
