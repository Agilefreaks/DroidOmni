package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.omnipaste.droidomni.BuildConfig;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.services.GoogleAnalyticsService;
import com.omnipaste.droidomni.services.SessionService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {
  private final EventBus eventBus = EventBus.getDefault();

  @ViewById
  public EditText authorizationCode;

  @ViewById
  public Button login;

  @ViewById
  public TextView authorizationCodeLink;

  @StringRes(R.string.login_invalid_code)
  public String loginInvalidCode;

  public String baseUrl = BuildConfig.BASE_URL;

  @Inject
  public SessionService sessionService;

  @Inject
  public GoogleAnalyticsService googleAnalyticsService;

  public LoginFragment() {
    DroidOmniApplication.inject(this);
  }

  @AfterViews
  public void afterViews() {
    googleAnalyticsService.trackHit(this.getClass().getName());

    authorizationCodeLink.setText(Html.fromHtml(getString(R.string.login_authorization_code_link, baseUrl)));
    authorizationCodeLink.setMovementMethod(LinkMovementMethod.getInstance());
  }

  @Click
  public void loginClicked() {
    login.setEnabled(false);
    doLogin(authorizationCode.getText().toString());
  }

  private void doLogin(String code) {
    sessionService.login(code,
        new Action1<AccessTokenDto>() {
          @Override
          public void call(AccessTokenDto accessTokenDto) {
            eventBus.post(new LoginEvent());
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            authorizationCode.setError(loginInvalidCode);
            login.setEnabled(true);
          }
        });
  }
}
