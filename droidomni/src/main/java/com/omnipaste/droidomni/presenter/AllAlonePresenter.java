package com.omnipaste.droidomni.presenter;

import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.prefs.TutorialClippingLocal;
import com.omnipaste.droidomni.ui.fragment.TutorialClippingLocalFragment_;
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
  private Devices devices;
  private BooleanPreference tutorialClippingLocalWasPlayed;
  private Subscription devicesSubscription;

  public interface View {
    void close();

    void addFragment(Fragment fragment);
  }

  @Inject
  public AllAlonePresenter(Devices devices, @TutorialClippingLocal BooleanPreference tutorialClippingLocalWasPlayed) {
    this.devices = devices;
    this.tutorialClippingLocalWasPlayed = tutorialClippingLocalWasPlayed;
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
              if (!tutorialClippingLocalWasPlayed.get()) {
                getView().addFragment(TutorialClippingLocalFragment_.builder().build());
              }

              getView().close();
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
