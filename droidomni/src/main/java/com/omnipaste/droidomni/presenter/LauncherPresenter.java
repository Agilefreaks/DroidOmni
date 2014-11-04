package com.omnipaste.droidomni.presenter;

import android.content.Context;

import com.omnipaste.droidomni.di.ActivityContext;
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
public class LauncherPresenter extends Presenter<LauncherPresenter.View> {

  public interface View {
    void finish();
  }

  private Navigator navigator;
  private SessionService sessionService;
  private DeviceService deviceService;
  private GetAccounts getAccounts;

  @Inject
  protected LauncherPresenter(@ActivityContext Context activityContext,
                              Navigator navigator,
                              SessionService sessionService,
                              DeviceService deviceService,
                              GetAccounts getAccounts) {
    super(activityContext);
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.deviceService = deviceService;
    this.getAccounts = getAccounts;
  }

  @Override
  public void initialize() {
    if (sessionService.isLogged()) {
      deviceService.init()
          .subscribeOn(scheduler)
          .observeOn(observeOnScheduler)
          .subscribe(
              // onNext
              new Action1<RegisteredDeviceDto>() {
                @Override public void call(RegisteredDeviceDto registeredDeviceDto) {
                  sessionService.setRegisteredDeviceDto(registeredDeviceDto);
                  navigator.openOmniActivity();
                  // finishView();
                }
              },
              // onError
              new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                }
              }
          );
    } else {
      sessionService
          .login(getAccounts.fromGoogle())
          .subscribe(
              // onNext
              new Action1<AccessTokenDto>() {
                @Override public void call(AccessTokenDto accessTokenDto) {
                  initialize();
                }
              },
              // onError
              new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                  navigator.openLoginActivity();
                  finishView();
                }
              }
          );
    }
  }

  private void finishView() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.finish();
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }
}
