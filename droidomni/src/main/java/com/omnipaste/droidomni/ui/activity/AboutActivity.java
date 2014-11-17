package com.omnipaste.droidomni.ui.activity;

import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.AboutPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity<AboutPresenter> {
  @Inject
  public AboutPresenter presenter;

  @ViewById
  public ListView aboutListView;

  @Override
  protected AboutPresenter getPresenter() {
    return presenter;
  }

  @SuppressWarnings("ConstantConditions")
  @AfterViews
  public void afterView() {
    aboutListView.setAdapter(presenter.getAboutAdapter());
  }
}
