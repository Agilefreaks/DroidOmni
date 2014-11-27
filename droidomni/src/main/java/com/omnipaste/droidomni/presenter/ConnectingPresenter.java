package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import com.omnipaste.droidomni.interaction.GetAccounts;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;
import rx.functions.Action1;

@Singleton
public class ConnectingPresenter extends Presenter<ConnectingPresenter.View> {

  private final Navigator navigator;
  private final SessionService sessionService;
  private final GetAccounts getAccounts;
  private final OmniServiceConnection omniServiceConnection;
  private boolean isInitiating = false;
  private Subscription startOmniServiceSubscription;
  private Subscription initSessionSubscription;

  public interface View {
    void finish();
  }

  @Inject
  public ConnectingPresenter(Navigator navigator,
                             SessionService sessionService,
                             GetAccounts getAccounts,
                             OmniServiceConnection omniServiceConnection) {
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.getAccounts = getAccounts;
    this.omniServiceConnection = omniServiceConnection;
  }

  @Override
  public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.attachActivity((Activity) view);
    }
  }

  @Override
  public void initialize() {
    if (isInitiating) {
      return;
    }
    isInitiating = true;

    if (sessionService.isLogged()) {
      startOmniService();
    } else {
      initSession();
    }
  }


  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
    if (startOmniServiceSubscription != null) {
      startOmniServiceSubscription.unsubscribe();
      startOmniServiceSubscription = null;
    }

    if (initSessionSubscription != null) {
      initSessionSubscription.unsubscribe();
      initSessionSubscription = null;
    }
  }

  private void initSession() {
    initSessionSubscription = sessionService
        .login(getAccounts.fromGoogle())
        .subscribe(
            // onNext
            new Action1<AccessTokenDto>() {
              @Override public void call(AccessTokenDto accessTokenDto) {
                startOmniService();
              }
            },
            // onError
            new Action1<Throwable>() {
              @Override public void call(Throwable throwable) {
                openLogin();
              }
            }
        );
  }

  private void startOmniService() {
    startOmniServiceSubscription = omniServiceConnection
        .startOmniService()
        .subscribe(
            // onNext
            new Action1<OmniServiceConnection.State>() {
              @Override public void call(OmniServiceConnection.State code) {
                if (code == OmniServiceConnection.State.error) {
                  openError(omniServiceConnection.getLastError());
                } else {
                  openOmni();
                }

                startOmniServiceSubscription.unsubscribe();
                startOmniServiceSubscription = null;
              }
            }
        );
  }

  private void openLogin() {
    navigator.openLoginActivity();
    isInitiating = false;
    finishView();
  }

  private void openOmni() {
    navigator.openOmniActivity();
    isInitiating = false;
    finishView();
  }

  private void openError(Throwable throwable) {
    navigator.openErrorActivity(throwable);
    isInitiating = false;
  }

  private void finishView() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.finish();
  }
}
