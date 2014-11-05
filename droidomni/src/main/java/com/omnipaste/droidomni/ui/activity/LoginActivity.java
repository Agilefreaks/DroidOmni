package com.omnipaste.droidomni.ui.activity;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.omnipaste.droidomni.BuildConfig;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.LoginPresenter;
import com.omnipaste.droidomni.presenter.Presenter;

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

  @Inject LoginPresenter presenter;

  @OptionsMenuItem public MenuItem actionLogin;

  @StringRes(R.string.login_invalid_code)
  public String loginInvalidCode;

  @ViewById public TextView authorizationCodeLink;

  @ViewById(R.id.authorization_code) public EditText authorizationCode;

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

  @Override
  protected Presenter getPresenter() {
    return presenter;
  }
}