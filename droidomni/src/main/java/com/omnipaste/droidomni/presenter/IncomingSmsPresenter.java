package com.omnipaste.droidomni.presenter;

import android.text.format.DateUtils;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

public class IncomingSmsPresenter extends Presenter<IncomingSmsPresenter.View> {
  public String from = DroidOmniApplication.getAppContext().getString(R.string.event_from);

  private final SmsMessageDto smsMessageDto;

  public interface View {
    public void setTitle(String title);

    public void setTime(CharSequence time);

    public void setContent(String content);
  }

  public IncomingSmsPresenter(SmsMessageDto smsMessageDto) {
    this.smsMessageDto = smsMessageDto;
  }

  @Override public void initialize() {
    getView().setTitle(String.format("%s %s", from, getNumberOrName(smsMessageDto)));
    getView().setTime(DateUtils.getRelativeTimeSpanString(smsMessageDto.getCreatedAt().getTimeInMillis()));
    getView().setContent(smsMessageDto.getContent());
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  public SmsMessageDto getSmsMessage() {
    return smsMessageDto;
  }

  private String getNumberOrName(SmsMessageDto smsMessageDto) {
    return smsMessageDto.getContactName() == null || smsMessageDto.getContactName().isEmpty() ? smsMessageDto.getPhoneNumber() : smsMessageDto.getContactName();
  }
}
