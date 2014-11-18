package com.omnipaste.droidomni.ui.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.ErrorPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_error)
public class ErrorActivity extends BaseActivity<ErrorPresenter> implements ErrorPresenter.View {
  public static String ERROR_EXTRA_KEY = "ERROR";

  private Throwable throwable;

  @Inject
  public ErrorPresenter presenter;

  @ViewById
  public TextView errorContent;

  @Override
  protected ErrorPresenter getPresenter() {
    return presenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      throwable = (Throwable) extras.getSerializable(ERROR_EXTRA_KEY);
    }
  }

  @AfterViews
  public void afterView() {
    errorContent.setText(throwable.getMessage());
  }
}
