package com.omnipaste.droidomni.fragments.clippings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.droidomni.adapters.IClippingAdapter;
import com.omnipaste.droidomni.events.OmniClipboardRefresh;
import com.omnipaste.droidomni.fragments.SwipeRefreshListFragment;
import com.omnipaste.droidomni.service.GoogleAnalyticsService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Subscription;

@EFragment
public abstract class ClippingsListFragment extends SwipeRefreshListFragment implements IListFragment {
  private final EventBus eventBus = EventBus.getDefault();
  private Subscription subscription;

  @StringRes
  public String ClippingsEmpty;

  @Inject
  public GoogleAnalyticsService googleAnalyticsService;

  protected ClippingsListFragment() {
    DroidOmniApplication.inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @AfterViews
  public void afterViews() {
    googleAnalyticsService.trackHit(this.getClass().getName());

    setListAdapter(new ClippingAdapter());

    Fragment parentFragment = getParentFragment();

    if (parentFragment instanceof ClippingsFragment) {
      ClippingsFragment clippingsFragment = (ClippingsFragment) parentFragment;
      subscription = observe(clippingsFragment.getObservable());
    }

    setColorScheme(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light);

    setOnRefreshListener(new ClippingsRefreshListener());
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    subscription.unsubscribe();
    subscription = null;
  }

  public IClippingAdapter getActualListAdapter() {
    return (IClippingAdapter) getListAdapter();
  }

  protected void add(ClippingDto clippingDto) {
    getActualListAdapter().add(clippingDto);
  }

  private class ClippingsRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
    @Override
    public void onRefresh() {
      eventBus.post(new OmniClipboardRefresh());
      setRefreshing(false);
    }
  }
}
