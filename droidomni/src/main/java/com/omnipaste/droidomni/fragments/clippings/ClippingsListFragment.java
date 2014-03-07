package com.omnipaste.droidomni.fragments.clippings;

import android.support.v4.app.ListFragment;

import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public abstract class ClippingsListFragment extends ListFragment {
  private final ClippingAdapter clippingAdapter;

  public ClippingsListFragment() {
    setRetainInstance(true);
    clippingAdapter = new ClippingAdapter();
  }

  @AfterViews
  public void afterViews() {
    if (getListAdapter() == null) {
      setListAdapter(clippingAdapter);
    }
  }

  public void add(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);
  }
}
