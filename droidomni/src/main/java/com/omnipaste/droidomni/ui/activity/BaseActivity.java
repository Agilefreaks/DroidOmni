package com.omnipaste.droidomni.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.presenter.Presenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public abstract class BaseActivity<TPresenter extends Presenter> extends ActionBarActivity {

  @ViewById
  protected Toolbar toolbar;

  @SuppressWarnings("unchecked")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectDependencies();
    getPresenter().attachView(this);
    getPresenter().initialize();
  }

  @AfterViews
  public void setupToolbar() {
    if (toolbar != null) {
      setSupportActionBar(toolbar);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setHomeButtonEnabled(true);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    getPresenter().pause();
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
    getPresenter().resume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    getPresenter().destroy();
  }

  protected abstract TPresenter getPresenter();

  private void injectDependencies() {
    DroidOmniApplication.inject(this);
  }
}
