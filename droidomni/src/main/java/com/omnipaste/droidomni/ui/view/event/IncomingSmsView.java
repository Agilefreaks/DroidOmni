package com.omnipaste.droidomni.ui.view.event;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.IncomingSmsPresenter;
import com.omnipaste.droidomni.presenter.SmsMessagesPresenter;
import com.omnipaste.droidomni.ui.view.HasSetup;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EViewGroup(R.layout.view_incoming_sms)
public class IncomingSmsView extends LinearLayout implements HasSetup<SmsMessageDto>, IncomingSmsPresenter.View {
  private IncomingSmsPresenter presenter;

  @ViewById
  public TextView incomingSmsTitle;

  @ViewById
  public TextView incomingSmsTime;

  @ViewById
  public TextView incomingSmsContent;

  @Inject
  public SmsMessagesPresenter smsMessagesPresenter;

  public IncomingSmsView(Context context) {
    super(context);

    DroidOmniApplication.inject(this);
  }

  @Override
  public void setUp(SmsMessageDto item) {
    presenter = new IncomingSmsPresenter(item);

    presenter.attachView(this);
    presenter.initialize();
  }

  @Click
  public void deleteClicked() {
    smsMessagesPresenter.remove(presenter.getSmsMessage());
  }

  @Override public void setTitle(String title) {
    incomingSmsTitle.setText(title);
  }

  @Override public void setTime(CharSequence time) {
    incomingSmsTime.setText(time);
  }

  @Override public void setContent(String content) {
    incomingSmsContent.setText(content);
  }
}
