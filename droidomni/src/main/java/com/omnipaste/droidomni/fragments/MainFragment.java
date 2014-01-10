package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action0;
import rx.util.functions.Action1;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {
  @ViewById
  public TextView fragmentMainText;

  @AfterViews
  public void afterViews() {
    new OmniApi("http://192.168.56.1:3000/api")
        .devices()
        .create("calinuswork@gmail.com", "42")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // OnNext
            new Action1<RegisteredDeviceDto>() {
              @Override
              public void call(RegisteredDeviceDto registeredDeviceDto) {
                fragmentMainText.setText(registeredDeviceDto.identifier);
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
