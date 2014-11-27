package com.omnipaste.droidomni.domain;

import com.omnipaste.omnicommon.dto.EventDto;

public class Event extends Command<EventDto> {
  public static Event add(EventDto eventDto) {
    return new Event(eventDto, Action.ADD);
  }

  public static Event remove(EventDto eventDto) {
    return new Event(eventDto, Action.REMOVE);
  }

  protected Event(EventDto item, Action action) {
    super(item, action);
  }
}
