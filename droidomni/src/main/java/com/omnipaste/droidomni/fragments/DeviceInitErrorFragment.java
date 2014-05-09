package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.omnipaste.droidomni.Helpers;
import com.omnipaste.droidomni.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_device_init_error)
public class DeviceInitErrorFragment extends Fragment {
  private String exceptionMessageText;

  @ViewById(R.id.device_init_error_exception_message)
  public TextView exceptionMessage;

  public void setExceptionMessage(Throwable errorMessage) {
    this.exceptionMessageText = errorMessage.toString();
  }

  @Click(R.id.device_init_error_retry)
  public void onRetryClicked() {
    Helpers.setFragment(getActivity(), R.id.main_container, DeviceInitFragment_.builder().build());
  }

  @AfterViews
  public void afterViews() {
    this.exceptionMessage.setText(exceptionMessageText);
  }

}
