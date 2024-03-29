package com.omnipaste.droidomni.ui.view.event;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.IncomingCallPresenter;
import com.omnipaste.droidomni.presenter.PhoneCallsPresenter;
import com.omnipaste.droidomni.ui.view.HasSetup;
import com.omnipaste.omnicommon.dto.PhoneCallDto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EViewGroup(R.layout.view_incoming_call)
public class IncomingCallView extends LinearLayout implements HasSetup<PhoneCallDto>, IncomingCallPresenter.View {
  private IncomingCallPresenter presenter;

  @Inject
  public PhoneCallsPresenter phoneCallsPresenter;

  @ViewById
  public TextView incomingCallTitle;

  @ViewById
  public TextView incomingCallTime;

  public IncomingCallView(Context context) {
    super(context);

    DroidOmniApplication.inject(this);
  }

  @Override
  public void setUp(PhoneCallDto phoneCallDto) {
    presenter = new IncomingCallPresenter(phoneCallDto);
    presenter.attachView(this);
    presenter.initialize();
  }

  @Click
  public void deleteClicked() {
    phoneCallsPresenter.remove(presenter.getPhoneCall());
  }

  @Override public void setTitle(String title) {
    incomingCallTitle.setText(title);
  }

  @Override public void setTime(CharSequence content) {
    incomingCallTime.setText(content);
  }
}
