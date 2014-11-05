package com.omnipaste.droidomni.ui.activity;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.LauncherPresenter;
import com.omnipaste.droidomni.presenter.Presenter;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_launcher)
public class LauncherActivity extends BaseActivity implements LauncherPresenter.View {
  @Inject LauncherPresenter presenter;

  @Override protected Presenter getPresenter() {
    return presenter;
  }
}
