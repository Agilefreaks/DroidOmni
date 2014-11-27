package com.omnipaste.droidomni.presenter;

import android.text.format.DateUtils;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.EventDto;

public class IncomingCallPresenter extends Presenter<IncomingCallPresenter.View> {
  private EventDto eventDto;

  public String from = DroidOmniApplication.getAppContext().getString(R.string.event_from);

  public interface View {
    public void setTitle(String title);

    public void setContent(CharSequence content);
  }

  public IncomingCallPresenter(EventDto eventDto) {
    this.eventDto = eventDto;
  }

  @Override public void initialize() {
    getView().setTitle(String.format("%s %s", from, getNumberOrName(eventDto)));
    getView().setContent(DateUtils.getRelativeTimeSpanString(eventDto.getCreatedAt().getTimeInMillis()));
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }

  public EventDto getEvent() {
    return eventDto;
  }

  private String getNumberOrName(EventDto eventDto) {
    return eventDto.getContactName() == null || eventDto.getContactName().isEmpty() ? eventDto.getPhoneNumber() : eventDto.getContactName();
  }
}
