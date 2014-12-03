package com.omnipaste.droidomni.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.presenter.FragmentPresenter;

public abstract class BaseFragment<TPresenter extends FragmentPresenter> extends Fragment {
  @SuppressWarnings("unchecked") @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectDependencies();
    getPresenter().attachView(this);
    getPresenter().attachActivity(this.getActivity());
    getPresenter().initialize();
  }

  @Override
  public void onPause() {
    super.onPause();

    getPresenter().pause();
  }

  @Override public void onResume() {
    super.onResume();

    getPresenter().resume();
  }

  @Override public void onDestroy() {
    super.onDestroy();

    getPresenter().destroy();
  }

  protected abstract TPresenter getPresenter();

  private void injectDependencies() {
    DroidOmniApplication.inject(this);
  }
}
