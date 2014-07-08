package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.services.FragmentService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_device_init_error)
public class DeviceInitErrorFragment extends Fragment {
  private String exceptionMessageText;
  private FragmentService fragmentService;

  @ViewById(R.id.device_init_error_exception_message)
  public TextView exceptionMessage;

  public DeviceInitErrorFragment() {
  }

  public static DeviceInitErrorFragment build(Throwable error, FragmentService fragmentService) {
    DeviceInitErrorFragment deviceInitErrorFragment = DeviceInitErrorFragment_.builder().build();
    deviceInitErrorFragment.setExceptionMessage(error);
    deviceInitErrorFragment.setFragmentService(fragmentService);

    return deviceInitErrorFragment;
  }

  public void setExceptionMessage(Throwable errorMessage) {
    this.exceptionMessageText = errorMessage.toString();
  }

  public void setFragmentService(FragmentService fragmentService) {
    this.fragmentService = fragmentService;
  }

  @Click(R.id.device_init_error_retry)
  public void onRetryClicked() {
    fragmentService.setFragment(getActivity(), R.id.main_container, DeviceInitFragment_.builder().build());
  }

  @AfterViews
  public void afterViews() {
    this.exceptionMessage.setText(exceptionMessageText);
  }
}
