package com.omnipaste.droidomni.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import com.omnipaste.droidomni.fragments.clippings.ClippingsListFragment;

import java.util.ArrayList;

public class ClippingsPagerAdapter extends FragmentPagerAdapter {
  private ArrayList<ClippingsListFragment> fragments;

  public ClippingsPagerAdapter(FragmentManager fm) {
    super(fm);

    fragments = new ArrayList<>();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return ((ClippingsListFragment) getFragment(position)).getTitle();
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return fragments.size();
  }

  public void addFragment(ClippingsListFragment fragment) {
    fragments.add(fragment);
    notifyDataSetChanged();
  }

  public ListFragment getFragment(int position) {
    return fragments.get(position);
  }
}
