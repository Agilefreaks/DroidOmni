package com.omnipaste.droidomni.fragments.clippings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.controllers.ClippingsFragmentController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

@EFragment(R.layout.fragment_clippings)
public class ClippingsFragment extends Fragment {

  @ViewById
  public ViewPager clippingsPager;

  @ViewById
  public PagerSlidingTabStrip clippingsTabs;

  @StringRes(R.string.clippings_tab_all)
  public String clippingsTabAll;

  @StringRes(R.string.clippings_tab_local)
  public String clippingsTabLocal;

  @StringRes(R.string.clippings_tab_cloud)
  public String clippingsTabCloud;

  @Inject
  public ClippingsFragmentController controller;

  public ClippingsFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DroidOmniApplication.inject(this);

    controller.run(this, savedInstanceState);
  }

  @AfterViews
  public void afterView() {
    controller.afterView();
  }
}
