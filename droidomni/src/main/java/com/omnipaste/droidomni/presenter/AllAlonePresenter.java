package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.prefs.WeAreAlone;
import com.omnipaste.omniapi.resource.v1.Devices;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

@Singleton
public class AllAlonePresenter extends FragmentPresenter<AllAlonePresenter.View> {
  private final Devices devices;
  private final BooleanPreference weAreAlone;
  private Subscription devicesSubscription;

  public interface View {
    void close();
  }

  @Inject
  public AllAlonePresenter(
      Devices devices,
      @WeAreAlone BooleanPreference weAreAlone) {
    this.devices = devices;
    this.weAreAlone = weAreAlone;
  }

  @Override public void initialize() {
    if (devicesSubscription != null) {
      return;
    }

    devicesSubscription = Observable.timer(3, 3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        .flatMap(new Func1<Long, Observable<RegisteredDeviceDto[]>>() {
          @Override public Observable<RegisteredDeviceDto[]> call(Long aLong) {
            return devices.get();
          }
        })
        .subscribe(new Action1<RegisteredDeviceDto[]>() {
          @Override
          public void call(RegisteredDeviceDto[] registeredDevices) {
            if (registeredDevices.length > 1) {
              getView().close();
              weAreAlone.set(false);
            }
          }
        });
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
    devicesSubscription.unsubscribe();
    devicesSubscription = null;
  }
}
