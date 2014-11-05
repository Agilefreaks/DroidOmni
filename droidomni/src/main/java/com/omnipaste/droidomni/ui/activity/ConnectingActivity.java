package com.omnipaste.droidomni.ui.activity;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.ConnectingPresenter;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_connecting)
public class ConnectingActivity extends BaseActivity<ConnectingPresenter> implements ConnectingPresenter.View {
  @Inject public ConnectingPresenter connectingPresenter;

  @Override protected ConnectingPresenter getPresenter() {
    return connectingPresenter;
  }
}
