package com.omnipaste.droidomni.fragments.clippings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import rx.Subscription;

@EFragment
public abstract class ClippingsListFragment extends ListFragment implements IListFragment {
  private final ClippingAdapter clippingAdapter;

  private Subscription subscription;

  @StringRes
  public String ClippingsEmpty;

  public ClippingsListFragment() {
    clippingAdapter = new ClippingAdapter();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_clippings_list, null);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putInt("curChoice", 1);
  }

  @AfterViews
  public void afterViews() {
    setListAdapter(clippingAdapter);

    Fragment parentFragment = getParentFragment();

    if (parentFragment instanceof ClippingsFragment) {
      subscription = observe(((ClippingsFragment) parentFragment).getObservable());
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    subscription.unsubscribe();
    subscription = null;
  }

  public ClippingAdapter getActualListAdapter() {
    return clippingAdapter;
  }

  protected void add(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);
  }
}
