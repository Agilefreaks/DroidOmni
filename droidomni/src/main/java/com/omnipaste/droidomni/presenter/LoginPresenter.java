package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class LoginPresenter extends Presenter<LoginPresenter.View> {

  private SessionService sessionService;
  private Navigator navigator;

  public interface View {
    void finish();

    void loginFailed();
  }

  @Inject
  protected LoginPresenter(SessionService sessionService, Navigator navigator) {
    this.sessionService = sessionService;
    this.navigator = navigator;
  }

  @Override public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.attachView((Activity) view);
    }
  }

  @Override public void initialize() {
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void login(String authorizationCode) {
    sessionService.login(authorizationCode)
        .subscribeOn(scheduler)
        .observeOn(observeOnScheduler)
        .subscribe(
            // onNext
            new Action1<AccessTokenDto>() {
              @Override public void call(AccessTokenDto accessTokenDto) {
                navigator.openLauncherActivity();
                finishView();
              }
            },
            // onError
            new Action1<Throwable>() {
              @Override public void call(Throwable throwable) {
                loginFailed();
              }
            }
        );
  }

  private void loginFailed() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.loginFailed();
  }

  private void finishView() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.finish();
  }
}
