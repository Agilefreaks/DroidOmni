package com.omnipaste.droidomni.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.presenter.Presenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public abstract class BaseActivity<TPresenter extends Presenter> extends ActionBarActivity {
  public static final int RESULT_CLOSE_ALL = 2;

  @ViewById
  protected Toolbar toolbar;

  @SuppressWarnings("unchecked")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectDependencies();
    getPresenter().attachView(this);
    getPresenter().initialize();

    track();
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_CLOSE_ALL) {
      setResult(RESULT_CLOSE_ALL);
      finish();
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  protected abstract TPresenter getPresenter();

  private void injectDependencies() {
    DroidOmniApplication.inject(this);
  }

  private void track() {
    Tracker tracker = ((DroidOmniApplication) getApplication()).getTracker();
    tracker.setScreenName(this.getLocalClassName());
    new HitBuilders.AppViewBuilder().build();
  }
}
