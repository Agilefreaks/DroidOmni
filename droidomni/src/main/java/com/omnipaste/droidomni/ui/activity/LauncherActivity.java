package com.omnipaste.droidomni.ui.activity;

import android.os.Bundle;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.LauncherPresenter;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_launcher)
public class LauncherActivity extends BaseActivity implements LauncherPresenter.View {
  @Inject LauncherPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    presenter.setView(this);
    presenter.initialize();
  }

  @Override protected void onPause() {
    super.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
  }
}
