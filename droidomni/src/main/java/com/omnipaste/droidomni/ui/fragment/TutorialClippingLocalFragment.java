package com.omnipaste.droidomni.ui.fragment;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.TutorialClippingLocalPresenter;

import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

@EFragment(R.layout.fragment_tutorial_clipping_local)
public class TutorialClippingLocalFragment extends BaseFragment<TutorialClippingLocalPresenter> implements TutorialClippingLocalPresenter.View {
  @Inject
  public TutorialClippingLocalPresenter tutorialClippingLocalPresenter;

  @Override protected TutorialClippingLocalPresenter getPresenter() {
    return tutorialClippingLocalPresenter;
  }

  @Override public void close() {
    getActivity()
        .getSupportFragmentManager()
        .beginTransaction()
        .remove(this)
        .commit();
  }
}
