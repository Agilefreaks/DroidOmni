package com.omnipaste.droidomni.presenter;

import android.text.format.DateUtils;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.PhoneCallDto;

public class IncomingCallPresenter extends Presenter<IncomingCallPresenter.View> {
  private PhoneCallDto phoneCallDto;

  public String from = DroidOmniApplication.getAppContext().getString(R.string.event_from);

  public interface View {
    public void setTitle(String title);

    public void setTime(CharSequence content);
  }

  public IncomingCallPresenter(PhoneCallDto phoneCallDto) {
    this.phoneCallDto = phoneCallDto;
  }

  @Override public void initialize() {
    getView().setTitle(String.format("%s %s", from, getNumberOrName(phoneCallDto)));
    getView().setTime(DateUtils.getRelativeTimeSpanString(phoneCallDto.getCreatedAt().getTimeInMillis()));
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }

  public PhoneCallDto getPhoneCall() {
    return phoneCallDto;
  }

  private String getNumberOrName(PhoneCallDto phoneCallDto) {
    return phoneCallDto.getContactName() == null || phoneCallDto.getContactName().isEmpty() ? phoneCallDto.getNumber() : phoneCallDto.getContactName();
  }
}
