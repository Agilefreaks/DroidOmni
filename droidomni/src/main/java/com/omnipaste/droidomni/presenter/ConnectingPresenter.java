package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.omnipaste.droidomni.interaction.CreateDevice;
import com.omnipaste.droidomni.interaction.DeviceIdentifier;
import com.omnipaste.droidomni.interaction.GetAccounts;
import com.omnipaste.droidomni.prefs.ContactsSynced;
import com.omnipaste.droidomni.prefs.DeviceId;
import com.omnipaste.droidomni.prefs.TutorialClippingCloud;
import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.droidomni.prefs.WeAreAlone;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.droidomni.ui.activity.ConnectingActivity_;
import com.omnipaste.omniapi.resource.v1.user.Devices;
import com.omnipaste.omniapi.resource.v1.user.User;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.DeviceDto;
import com.omnipaste.omnicommon.dto.UserDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;
import com.omnipaste.omnicommon.prefs.StringPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.F;
import fj.data.Array;
import fj.data.Option;
import retrofit.RetrofitError;
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
  private final User user;
  private final CreateDevice createDevice;
  private final StringPreference deviceId;
  private final String deviceIdentifier;
  private final BooleanPreference contactsSynced;
  private final BooleanPreference weAreAlone;
  private final BooleanPreference tutorialClippingLocal;
  private BooleanPreference tutorialClippingCloud;
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
                             User user,
                             CreateDevice createDevice,
                             @DeviceId StringPreference deviceId,
                             @DeviceIdentifier String deviceIdentifier,
                             @ContactsSynced BooleanPreference contactsSynced,
                             @WeAreAlone BooleanPreference weAreAlone,
                             @TutorialClippingLocal BooleanPreference tutorialClippingLocal,
                             @TutorialClippingCloud BooleanPreference tutorialClippingCloud) {
    this.navigator = navigator;
    this.sessionService = sessionService;
    this.getAccounts = getAccounts;
    this.omniServiceConnection = omniServiceConnection;
    this.devices = devices;
    this.user = user;
    this.createDevice = createDevice;
    this.deviceId = deviceId;
    this.deviceIdentifier = deviceIdentifier;
    this.contactsSynced = contactsSynced;
    this.weAreAlone = weAreAlone;
    this.tutorialClippingLocal = tutorialClippingLocal;
    this.tutorialClippingCloud = tutorialClippingCloud;
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, ConnectingActivity_.class);
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

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
  }

  private void initSession() {
    sessionService
      .login(getAccounts.fromGoogle())
      .take(1)
      .subscribe(
        // onNext
        new Action1<AccessTokenDto>() {
          @Override
          public void call(AccessTokenDto accessTokenDto) {
            startOmniService();
          }
        },
        // onError
        new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            openLogin();
          }
        }
      );
  }

  private void startOmniService() {
    devices.get()
      .flatMap(new Func1<DeviceDto[], Observable<DeviceDto>>() {
        @Override
        public Observable<DeviceDto> call(DeviceDto[] devices) {
          final Array<DeviceDto> fjDevices = Array.array(devices);

          setTutorials(fjDevices);
          return createDeviceIfNeeded(fjDevices);
        }
      })
      .flatMap(new Func1<DeviceDto, Observable<UserDto>>() {
        @Override
        public Observable<UserDto> call(DeviceDto deviceDto) {
          if (!deviceId.get().equals(deviceDto.getId())) {
            deviceId.set(deviceDto.getId());
          }
          sessionService.setDeviceDto(deviceDto);

          return user.get();
        }
      })
      .flatMap(new Func1<UserDto, Observable<OmniServiceConnection.State>>() {
        @Override
        public Observable<OmniServiceConnection.State> call(UserDto userDto) {
          contactsSynced.set(userDto.getContactsUpdatedAt() != null);

          return omniServiceConnection.startOmniService();
        }
      })
      .takeFirst(new Func1<OmniServiceConnection.State, Boolean>() {
        @Override
        public Boolean call(OmniServiceConnection.State state) {
          return state == OmniServiceConnection.State.started || state == OmniServiceConnection.State.error;
        }
      })
      .subscribeOn(getScheduler())
      .observeOn(getObserveOnScheduler())
      .subscribe(
        // onNext
        new Action1<OmniServiceConnection.State>() {
          @Override
          public void call(OmniServiceConnection.State code) {
            if (code == OmniServiceConnection.State.error) {
              openError(omniServiceConnection.getLastError());
            } else {
              openOmni();
            }
          }
        },
        // onError
        new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            if (throwable instanceof RetrofitError) {
              sessionService.logout();
              openLogin();
            } else {
              openError(throwable);
            }
          }
        }
      );
  }

  private Observable<DeviceDto> createDeviceIfNeeded(Array<DeviceDto> devices) {
    Option<DeviceDto> currentDevice = devices.find(new F<DeviceDto, Boolean>() {
      @Override
      public Boolean f(DeviceDto deviceDto) {
        return deviceDto.getName().equals(deviceIdentifier) ||
          deviceDto.getId().equals(deviceId.get());
      }
    });

    if (currentDevice.isSome()) {
      return Observable.just(currentDevice.some());
    }
    else {
      return createDevice.run();
    }
  }

  private void setTutorials(Array<DeviceDto> devices) {
    if (devices.isEmpty()) {
      weAreAlone.set(true);
      tutorialClippingCloud.set(false);
      tutorialClippingLocal.set(true);
    } else if (devices.length() == 1) {
      tutorialClippingLocal.set(false);
      tutorialClippingCloud.set(true);
    }
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
