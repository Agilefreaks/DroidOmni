package com.omnipaste.droidomni.ui.activity;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.LauncherPresenter;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_launcher)
public class LauncherActivity extends BaseActivity<LauncherPresenter> implements LauncherPresenter.View {
  @Inject public LauncherPresenter presenter;

  @Override protected LauncherPresenter getPresenter() {
    return presenter;
  }
}
