package com.omnipaste.eventsprovider.listeners;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.omnipaste.eventsprovider.events.TelephonyEvent;
import com.omnipaste.omnicommon.dto.IncomingCallEventDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import de.greenrobot.event.EventBus;

public class OmniPhoneStateListener extends PhoneStateListener {
  private final EventBus eventBus = EventBus.getDefault();

  @Override
  public void onCallStateChanged(int state, java.lang.String incomingNumber) {
    super.onCallStateChanged(state, incomingNumber);

    if (state == TelephonyManager.CALL_STATE_RINGING) {
      TelephonyEventDto telephonyEventDto = new TelephonyEventDto(
          TelephonyEventDto.TelephonyEventType.incomingCall,
          new IncomingCallEventDto().setPhoneNumber(incomingNumber));
      eventBus.post(new TelephonyEvent(telephonyEventDto));
    }
  }
}
