package com.omnipaste.droidomni.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.controllers.ClippingsFragmentController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_clippings)
public class ClippingsFragment extends Fragment {

  @ViewById
  public ListView clippings;

  @Inject
  public ClippingsFragmentController controller;

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
