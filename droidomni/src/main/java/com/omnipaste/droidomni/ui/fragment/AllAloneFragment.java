package com.omnipaste.droidomni.ui.fragment;

import android.support.v4.app.Fragment;

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

  @Override public void addFragment(Fragment fragment) {
    getActivity()
        .getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.omni_container, fragment)
        .commit();
  }
}
