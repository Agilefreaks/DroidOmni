package com.omnipaste.eventsprovider;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.omnipaste.omnicommon.dto.TelephonyNotificationDto;

import rx.Observable;
import rx.subjects.PublishSubject;

public class OmniPhoneStateListener extends PhoneStateListener{
  private PublishSubject<TelephonyNotificationDto> omniPhoneStateSubject;

  public OmniPhoneStateListener() {
    this.omniPhoneStateSubject = PublishSubject.create();
  }

  public Observable<TelephonyNotificationDto> getObservable() {
    return omniPhoneStateSubject;
  }

  @Override
  public void onCallStateChanged(int state, java.lang.String incomingNumber) {
    super.onCallStateChanged(state, incomingNumber);

    if (state == TelephonyManager.CALL_STATE_RINGING) {
      TelephonyNotificationDto telephonyNotificationDto = new TelephonyNotificationDto(TelephonyNotificationDto.TelephonyNotificationType.incomingCall, incomingNumber);
      omniPhoneStateSubject.onNext(telephonyNotificationDto);
    }
  }
}
