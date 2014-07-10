package com.omnipaste.droidomni.fragments.clippings;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.StringRes;

import rx.Observable;

@EFragment
public abstract class ClippingsListFragment extends ListFragment {
  private final ClippingAdapter clippingAdapter;
  private String title;

  @StringRes
  public String ClippingsEmpty;

  public ClippingsListFragment() {
    setRetainInstance(true);
    clippingAdapter = new ClippingAdapter();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_clippings_list, null);
  }

  @AfterViews
  public void afterViews() {
    getListView().setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.empty, null));

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
