package com.omnipaste.eventsprovider.listeners;

import com.omnipaste.omnicommon.dto.TelephonyEventDto;

public interface EventsReceiver {
  public void post(TelephonyEventDto telephonyEventDto);
}
