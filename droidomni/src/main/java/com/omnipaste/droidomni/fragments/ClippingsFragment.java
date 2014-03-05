package com.omnipaste.droidomni.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_clippings)
public class ClippingsFragment extends Fragment {
  private ClippingAdapter clippingAdapter;

  @ViewById
  public ListView clippings;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);

    clippingAdapter = new ClippingAdapter();
  }

  @AfterViews
  public void afterView() {
    if (clippings.getAdapter() == null) {
      clippings.setAdapter(clippingAdapter);
    }
  }

  public void setClipping(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);
  }
}
