package com.omnipaste.eventsprovider.listeners;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import rx.Observable;
import rx.subjects.PublishSubject;

public class OmniPhoneStateListener extends PhoneStateListener{
  private PublishSubject<TelephonyEventDto> omniPhoneStateSubject;

  public OmniPhoneStateListener() {
    this.omniPhoneStateSubject = PublishSubject.create();
  }

  public Observable<TelephonyEventDto> getObservable() {
    return omniPhoneStateSubject;
  }

  @Override
  public void onCallStateChanged(int state, java.lang.String incomingNumber) {
    super.onCallStateChanged(state, incomingNumber);

    if (state == TelephonyManager.CALL_STATE_RINGING) {
      TelephonyEventDto telephonyEventDto = new TelephonyEventDto(TelephonyEventDto.TelephonyEventType.incomingCall, incomingNumber);
      omniPhoneStateSubject.onNext(telephonyEventDto);
    }
  }
}
