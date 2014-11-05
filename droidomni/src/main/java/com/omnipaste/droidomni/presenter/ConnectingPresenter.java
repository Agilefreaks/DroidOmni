package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import com.omnipaste.droidomni.interactions.GetAccounts;
import com.omnipaste.droidomni.service.DeviceService;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class ConnectingPresenter extends Presenter<ConnectingPresenter.View> {

  private Navigator navigator;
  private SessionService sessionService;
  private DeviceService deviceService;
  private GetAccounts getAccounts;
  private boolean isInitiating = false;

  public interface View {
    void finish();
  }

  @Inject
  public ConnectingPresenter(Navigator navigator,
                             SessionService sessionService,
                             DeviceService deviceService,
                             GetAccounts getAccounts) {
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.deviceService = deviceService;
    this.getAccounts = getAccounts;
  }

  @Override
  public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.attachView((Activity) view);
    }
  }

  @Override
  public void initialize() {
    if (isInitiating) {
      return;
    }
    isInitiating = true;

    if (sessionService.isLogged()) {
      initDevice();
    } else {
      initSession();
    }
  }


  @Override public void resume() {
  }

  @Override public void pause() {
  }

  private void initSession() {
    sessionService
        .login(getAccounts.fromGoogle())
        .subscribe(
            // onNext
            new Action1<AccessTokenDto>() {
              @Override public void call(AccessTokenDto accessTokenDto) {
                initDevice();
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

  private void initDevice() {
    deviceService.init()
        .subscribeOn(scheduler)
        .observeOn(observeOnScheduler)
        .subscribe(
            // onNext
            new Action1<RegisteredDeviceDto>() {
              @Override public void call(RegisteredDeviceDto registeredDeviceDto) {
                sessionService.setRegisteredDeviceDto(registeredDeviceDto);
                openOmni();
              }
            },
            // onError
            new Action1<Throwable>() {
              @Override public void call(Throwable throwable) {
                openError(throwable);
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
