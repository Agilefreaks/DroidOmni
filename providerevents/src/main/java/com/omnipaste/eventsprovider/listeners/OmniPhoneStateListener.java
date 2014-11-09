package com.omnipaste.eventsprovider.listeners;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.omnipaste.omnicommon.dto.IncomingCallEventDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OmniPhoneStateListener extends PhoneStateListener {
  private TelephonyManager telephonyManager;
  private EventsReceiver receiver;

  @Inject
  public OmniPhoneStateListener(TelephonyManager telephonyManager) {
    this.telephonyManager = telephonyManager;
  }

  public void start(EventsReceiver receiver) {
    telephonyManager.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
    this.receiver = receiver;
  }

  public void stop() {
    telephonyManager.listen(this, PhoneStateListener.LISTEN_NONE);
    receiver = null;
  }

  @Override
  public void onCallStateChanged(int state, java.lang.String incomingNumber) {
    super.onCallStateChanged(state, incomingNumber);

    if (state == TelephonyManager.CALL_STATE_RINGING) {
      TelephonyEventDto telephonyEventDto = new TelephonyEventDto(
          TelephonyEventDto.TelephonyEventType.incomingCall,
          new IncomingCallEventDto().setPhoneNumber(incomingNumber));
      receiver.post(telephonyEventDto);
    }
  }
}
