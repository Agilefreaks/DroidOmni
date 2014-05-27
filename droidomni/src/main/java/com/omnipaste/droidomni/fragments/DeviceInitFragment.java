package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.DeviceInitErrorEvent;
import com.omnipaste.droidomni.events.DeviceInitEvent;
import com.omnipaste.droidomni.events.SignOutEvent;
import com.omnipaste.droidomni.services.DeviceService;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_device_init)
public class DeviceInitFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();

  public DeviceInitFragment() {
  }

  @AfterViews
  public void afterViews() {
    new DeviceService().init()
        .doOnError(new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            // something was very wrong the user should login back in
            eventBus.post(new SignOutEvent());
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // OnNext
            new Action1<RegisteredDeviceDto>() {
              @Override
              public void call(RegisteredDeviceDto registeredDeviceDto) {
                eventBus.post(new DeviceInitEvent(registeredDeviceDto));
              }
            },
            // OnError
            new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                eventBus.post(new DeviceInitErrorEvent(throwable));
              }
            }
        );
  }
}
