package com.omnipaste.droidomni.ui.fragment;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.TutorialClippingCloudPresenter;

import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

@EFragment(R.layout.fragment_tutorial_clipping_cloud)
public class TutorialClippingCloudFragment extends BaseFragment<TutorialClippingCloudPresenter> implements TutorialClippingCloudPresenter.View {
  @Inject
  public TutorialClippingCloudPresenter tutorialClippingCloudPresenter;

  @Override protected TutorialClippingCloudPresenter getPresenter() {
    return tutorialClippingCloudPresenter;
  }

  @Override public void close() {
    getActivity()
        .getSupportFragmentManager()
        .beginTransaction()
        .remove(this)
        .commit();
  }
}
