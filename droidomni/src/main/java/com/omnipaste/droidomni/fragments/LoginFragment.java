package com.omnipaste.droidomni.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.services.LoginService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();

  @ViewById
  public EditText authorizationCode;

  @ViewById
  Button login;

  @StringRes
  public String loginInvalidCode;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Click
  public void loginClicked() {
    if (authorizationCode.getText() == null) {
      return;
    }

    login.setEnabled(false);

    new LoginService().login(authorizationCode.getText().toString())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // OnNext
            new Action1<AccessTokenDto>() {
              @Override
              public void call(AccessTokenDto accessTokenDto) {
                eventBus.post(new LoginEvent(accessTokenDto));
              }
            },
            // OnError
            new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                authorizationCode.setError(loginInvalidCode);
                login.setEnabled(true);
              }
            },
            // OnComplete
            new Action0() {
              @Override
              public void call() {
              }
            }
        );
  }
}
