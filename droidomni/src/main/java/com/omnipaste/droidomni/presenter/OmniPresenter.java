package com.omnipaste.droidomni.presenter;

import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.ui.fragment.ActivityFragment_;
import com.omnipaste.droidomni.ui.fragment.AllAloneFragment_;
import com.omnipaste.omniapi.resource.v1.Devices;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class OmniPresenter extends Presenter<OmniPresenter.View> {
  private Devices devices;

  public interface View {
    void replaceFragment(Fragment activityFragment);

    void addFragment(Fragment fragment);
  }

  @Inject
  public OmniPresenter(Devices devices) {
    this.devices = devices;
  }

  @Override
  public void initialize() {
    replaceFragment(ActivityFragment_.builder().build());
    checkIfWeAreAlone();
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
  }

  private void checkIfWeAreAlone() {
    devices.get()
        .subscribeOn(scheduler)
        .observeOn(observeOnScheduler)
        .subscribe(new Action1<RegisteredDeviceDto[]>() {
          @Override
          public void call(RegisteredDeviceDto[] registeredDevices) {
            if (registeredDevices.length == 1) {
              addFragment(AllAloneFragment_.builder().build());
            }
          }
        });
  }

  private void replaceFragment(Fragment fragment) {
    View view = getView();
    if (view == null) {
      return;
    }

    view.replaceFragment(fragment);
  }

  private void addFragment(Fragment fragment) {
    View view = getView();
    if (view == null) {
      return;
    }

    view.addFragment(fragment);
  }
}
