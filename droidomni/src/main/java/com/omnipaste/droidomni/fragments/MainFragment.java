package com.omnipaste.droidomni.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.FragmentChanged;
import com.omnipaste.droidomni.services.DeviceService;
import com.omnipaste.droidomni.services.OmniService_;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.util.functions.Action0;
import rx.util.functions.Action1;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {
  public final static String DEVICE_IDENTIFIER_EXTRA_KEY = "device_identifier";

  private EventBus eventBus = EventBus.getDefault();

  @ViewById
  public TextView fragmentMainText;

  @AfterViews
  public void afterViews() {
    DroidOmniApplication.inject(this);

    new DeviceService().init()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // OnNext
            new Action1<RegisteredDeviceDto>() {
              @Override
              public void call(RegisteredDeviceDto registeredDeviceDto) {
                Intent service = new Intent(getActivity(), OmniService_.class);
                service.putExtra(DEVICE_IDENTIFIER_EXTRA_KEY, registeredDeviceDto.getIdentifier());

                getActivity().startService(service);
                eventBus.post(new FragmentChanged(ClippingsFragment_.builder().build()));
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
