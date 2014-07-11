package com.omnipaste.droidomni.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.omnipaste.droidomni.fragments.clippings.ClippingsListFactory;
import com.omnipaste.droidomni.fragments.clippings.IClippingsListFactory;
import com.omnipaste.droidomni.fragments.clippings.IListFragment;

public class ClippingsPagerAdapter extends FragmentPagerAdapter {
  public IClippingsListFactory clippingsListFactory;
  private Context context;

  public ClippingsPagerAdapter(FragmentManager fm, Context context) {
    super(fm);
    this.context = context;

    clippingsListFactory = new ClippingsListFactory();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return context.getString(((IListFragment) getItem(position)).getTitle());
  }

  @Override
  public Fragment getItem(int position) {
    return clippingsListFactory.getFragment(position);
  }

  @Override
  public int getCount() {
    return clippingsListFactory.getCount();
  }
}
