package com.omnipaste.droidomni.ui.fragment;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.AllAlonePresenter;

import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

@EFragment(R.layout.fragment_all_alone)
public class AllAloneFragment extends BaseFragment<AllAlonePresenter> implements AllAlonePresenter.View {
  @Inject
  public AllAlonePresenter presenter;

  @Override protected AllAlonePresenter getPresenter() {
    return presenter;
  }

  @Override public void close() {
    getActivity()
        .getSupportFragmentManager()
        .beginTransaction()
        .remove(this)
        .commit();
  }
}
