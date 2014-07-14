package com.omnipaste.droidomni.fragments.clippings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.ClippingsPagerAdapter;
import com.omnipaste.droidomni.controllers.ClippingsFragmentController;
import com.omnipaste.droidomni.controllers.ClippingsFragmentControllerImpl;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Observable;

@EFragment(R.layout.fragment_clippings)
public class ClippingsFragment extends Fragment {

  public static final String CLIPPINGS_PARCEL = "clippings";

  @ViewById
  public ViewPager clippingsPager;

  @ViewById
  public PagerSlidingTabStrip clippingsTabs;

  public ClippingsFragmentController controller;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    controller = new ClippingsFragmentControllerImpl();
    DroidOmniApplication.inject(controller);

    controller.run(this, savedInstanceState);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    controller.stop();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArray(CLIPPINGS_PARCEL, controller.getClippings().toArray(new ClippingDto[controller.getClippings().size()]));
  }

  @AfterViews
  public void afterView() {
    controller.afterView();
  }

  public void setAdapter(ClippingsPagerAdapter adapter) {
    clippingsPager.setAdapter(adapter);
  }

  public void setViewPager() {
    clippingsTabs.setViewPager(clippingsPager);
  }

  public Observable<ClippingDto> getObservable() {
    return controller.getObservable();
  }
}
