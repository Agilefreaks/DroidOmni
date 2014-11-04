package com.omnipaste.droidomni.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.omnipaste.droidomni.BuildConfig;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.LoginPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

@OptionsMenu(R.menu.activity_login_actions)
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements LoginPresenter.View {
  public final String baseUrl = BuildConfig.BASE_URL;

  @ViewById public TextView authorizationCodeLink;

  @ViewById(R.id.authorization_code) public EditText authorizationCode;

  @OptionsMenuItem public MenuItem actionLogin;

  @Inject LoginPresenter presenter;

  @StringRes(R.string.login_invalid_code)
  public String loginInvalidCode;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    presenter.setView(this);
    presenter.initialize();
  }

  @Override
  public void setupToolbar() {
    super.setupToolbar();
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
  }

  @AfterViews
  public void afterViews() {
    authorizationCodeLink.setText(Html.fromHtml(getString(R.string.login_authorization_code_link, baseUrl)));
    authorizationCodeLink.setMovementMethod(LinkMovementMethod.getInstance());
  }

  @OptionsItem
  public void action_login() {
    actionLogin.setActionView(new ProgressBar(this));
    presenter.login(authorizationCode.getText().toString());
  }

  @Override
  public void loginFailed() {
    actionLogin.setActionView(null);
    authorizationCode.setError(loginInvalidCode);
  }
}