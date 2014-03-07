package com.omnipaste.droidomni.fragments.clippings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.controllers.ActionBarController;
import com.omnipaste.droidomni.controllers.ClippingsFragmentController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_clippings)
public class ClippingsFragment extends Fragment {

  @ViewById
  public ViewPager clippingsPager;

  @Inject
  public ClippingsFragmentController controller;

  public ActionBarController actionBarController;

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
