package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.services.GoogleAnalyticsService;
import com.omnipaste.droidomni.services.LoginService;
import com.omnipaste.droidomni.services.SessionService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();
  private String authorizationCodeValue;

  @ViewById
  public EditText authorizationCode;

  @ViewById
  public Button login;

  @StringRes(R.string.login_invalid_code)
  public String loginInvalidCode;

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

    if (authorizationCodeValue != null) {
      authorizationCode.setText(authorizationCodeValue);
      loginClicked();
    }
  }

  public void setAuthorizationCode(String authorizationCode) {
    this.authorizationCodeValue = authorizationCode;
  }

  @Click
  public void loginClicked() {
    if (authorizationCode.getText() == null) {
      return;
    }

    login.setEnabled(false);
    doLogin(authorizationCode.getText().toString());
  }

  private void doLogin(String code) {
    new LoginService().login(code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // OnNext
            new Action1<AccessTokenDto>() {
              @Override
              public void call(AccessTokenDto accessTokenDto) {
                sessionService.login(accessTokenDto);
                eventBus.post(new LoginEvent());
              }
            },
            // OnError
            new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                authorizationCode.setError(loginInvalidCode);
                login.setEnabled(true);
              }
            }
        );
  }
}
