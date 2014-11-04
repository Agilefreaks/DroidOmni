package com.omnipaste.droidomni.presenter;

import android.content.Context;

import com.omnipaste.droidomni.di.ActivityContext;
import com.omnipaste.droidomni.service.DeviceService;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
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

  @Inject
  protected LauncherPresenter(@ActivityContext Context activityContext, Navigator navigator,
                              SessionService sessionService, DeviceService deviceService) {
    super(activityContext);
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.deviceService = deviceService;
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
                  getView().finish();
                }
              },
              // onError
              new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {

                }
              }
          );
    } else {
      navigator.openLoginActivity();
    }
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }
}
