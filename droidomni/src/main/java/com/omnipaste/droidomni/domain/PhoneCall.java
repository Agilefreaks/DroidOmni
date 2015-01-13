package com.omnipaste.droidomni.domain;

import com.omnipaste.omnicommon.dto.PhoneCallDto;

public class PhoneCall extends Command<PhoneCallDto> {
  public static PhoneCall add(PhoneCallDto phoneCallDto) {
    return new PhoneCall(phoneCallDto, Action.ADD);
  }

  public static PhoneCall remove(PhoneCallDto phoneCallDto) {
    return new PhoneCall(phoneCallDto, Action.REMOVE);
  }

  protected PhoneCall(PhoneCallDto item, Action action) {
    super(item, action);
  }
}
