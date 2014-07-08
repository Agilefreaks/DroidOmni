package com.omnipaste.droidomni.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.services.FragmentService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_device_init_error)
public class DeviceInitErrorFragment extends Fragment {
  private String exceptionMessageText;
  private EventBus eventBus = EventBus.getDefault();

  @ViewById(R.id.device_init_error_exception_message)
  public TextView exceptionMessage;

  public DeviceInitErrorFragment() {
  }

  public static DeviceInitErrorFragment build(Throwable error) {
    DeviceInitErrorFragment deviceInitErrorFragment = DeviceInitErrorFragment_.builder().build();
    deviceInitErrorFragment.setExceptionMessage(error);

    return deviceInitErrorFragment;
  }

  public void setExceptionMessage(Throwable errorMessage) {
    this.exceptionMessageText = errorMessage.toString();
  }

  @Click(R.id.device_init_error_retry)
  public void onRetryClicked() {
    eventBus.post(new LoginEvent());
  }

  @AfterViews
  public void afterViews() {
    this.exceptionMessage.setText(exceptionMessageText);
  }
}
