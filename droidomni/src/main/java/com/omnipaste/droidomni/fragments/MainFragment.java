package com.omnipaste.droidomni.fragments;

import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action0;
import rx.util.functions.Action1;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {
  private Configuration configuration;

  @ViewById
  public TextView fragmentMainText;

  @StringRes
  public String apiUrl;

  @Inject
  public ConfigurationService configurationService;

  @AfterViews
  public void afterViews() {
    DroidOmniApplication.inject(this);
    configuration = configurationService.getConfiguration();

    createDevice();
  }

  private void createDevice() {
    new OmniApi(apiUrl)
        .devices()
        .create(configuration.channel, getIdentifier())
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

  private String getIdentifier() {
    String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
    return String.format("%s-%s", Build.MODEL, android_id);
  }
}
