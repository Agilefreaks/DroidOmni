package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import com.omnipaste.droidomni.interaction.CreateDevice;
import com.omnipaste.droidomni.interaction.GetAccounts;
import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.droidomni.prefs.WeAreAlone;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omniapi.resource.v1.Devices;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@Singleton
public class ConnectingPresenter extends Presenter<ConnectingPresenter.View> {

  private final Navigator navigator;
  private final SessionService sessionService;
  private final GetAccounts getAccounts;
  private final OmniServiceConnection omniServiceConnection;
  private final Devices devices;
  private final CreateDevice createDevice;
  private final BooleanPreference weAreAlone;
  private final BooleanPreference tutorialClippingLocal;
  private boolean isInitiating = false;

  public interface View {
    void finish();
  }

  @Inject
  public ConnectingPresenter(Navigator navigator,
                             SessionService sessionService,
                             GetAccounts getAccounts,
                             OmniServiceConnection omniServiceConnection,
                             Devices devices,
                             CreateDevice createDevice,
                             @WeAreAlone BooleanPreference weAreAlone,
                             @TutorialClippingLocal BooleanPreference tutorialClippingLocal) {
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.getAccounts = getAccounts;
    this.omniServiceConnection = omniServiceConnection;
    this.devices = devices;
    this.createDevice = createDevice;
    this.weAreAlone = weAreAlone;
    this.tutorialClippingLocal = tutorialClippingLocal;
  }

  @Override
  public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.setContext((Activity) view);
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
  }

  private void initSession() {
    sessionService
        .login(getAccounts.fromGoogle())
        .take(1)
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
    devices.get()
        .flatMap(new Func1<RegisteredDeviceDto[], Observable<RegisteredDeviceDto>>() {
          @Override
          public Observable<RegisteredDeviceDto> call(RegisteredDeviceDto[] registeredDevices) {
            if (registeredDevices.length == 0) {
              weAreAlone.set(true);
              tutorialClippingLocal.set(false);
            } else if (registeredDevices.length == 1) {
              tutorialClippingLocal.set(false);
            }

            return createDevice.run();
          }
        })
        .flatMap(new Func1<RegisteredDeviceDto, Observable<OmniServiceConnection.State>>() {
          @Override
          public Observable<OmniServiceConnection.State> call(RegisteredDeviceDto registeredDeviceDto) {
            sessionService.setRegisteredDeviceDto(registeredDeviceDto);
            return omniServiceConnection.startOmniService();
          }
        })
        .observeOn(observeOnScheduler)
        .takeFirst(new Func1<OmniServiceConnection.State, Boolean>() {
          @Override public Boolean call(OmniServiceConnection.State state) {
            return state == OmniServiceConnection.State.started || state == OmniServiceConnection.State.error;
          }
        })
        .subscribe(
            // onNext
            new Action1<OmniServiceConnection.State>() {
              @Override public void call(OmniServiceConnection.State code) {
                if (code == OmniServiceConnection.State.error) {
                  openError(omniServiceConnection.getLastError());
                } else {
                  openOmni();
                }
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
