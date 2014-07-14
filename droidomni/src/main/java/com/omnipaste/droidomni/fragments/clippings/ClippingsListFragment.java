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
  private Subscription subscription;

  @StringRes
  public String ClippingsEmpty;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_clippings_list, null);
  }

  @AfterViews
  public void afterViews() {
    setListAdapter(new ClippingAdapter());

    Fragment parentFragment = getParentFragment();

    if (parentFragment instanceof ClippingsFragment) {
      ClippingsFragment clippingsFragment = (ClippingsFragment) parentFragment;
      subscription = observe(clippingsFragment.getObservable());
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    subscription.unsubscribe();
    subscription = null;
  }

  public ClippingAdapter getActualListAdapter() {
    return (ClippingAdapter) getListAdapter();
  }

  protected void add(ClippingDto clippingDto) {
    getActualListAdapter().add(clippingDto);
  }
}
