package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.DeviceInitEvent;
import com.omnipaste.droidomni.services.DeviceService;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.util.functions.Action0;
import rx.util.functions.Action1;

@EFragment(R.layout.fragment_device_init)
public class DeviceInitFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();

  @ViewById
  public TextView fragmentMainText;

  @AfterViews
  public void afterViews() {
    new DeviceService().init()
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
              }
            },
            // OnComplete
            new Action0() {
              @Override
              public void call() {
              }
            }
        );
  }
}
