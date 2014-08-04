package com.omnipaste.eventsprovider.events;

import com.omnipaste.omnicommon.dto.TelephonyEventDto;

public class TelephonyEvent {
  private TelephonyEventDto telephonyEventDto;

  public TelephonyEvent(TelephonyEventDto telephonyEventDto) {
    this.telephonyEventDto = telephonyEventDto;
  }

  public TelephonyEventDto getTelephonyEventDto() {
    return telephonyEventDto;
  }
}
