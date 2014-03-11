package com.omnipaste.droidomni.fragments.clippings;

import android.support.v4.app.ListFragment;

import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import rx.Observable;

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

  public ClippingAdapter getActualListAdapter() {
    return clippingAdapter;
  }

  public abstract void observe(Observable<ClippingDto> observable);

  protected void add(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);
  }
}
