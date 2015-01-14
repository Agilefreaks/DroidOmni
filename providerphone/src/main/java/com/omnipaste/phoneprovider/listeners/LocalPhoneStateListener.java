package com.omnipaste.phoneprovider.listeners;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.phoneprovider.ContactsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalPhoneStateListener extends PhoneStateListener implements Listener {
  private TelephonyManager telephonyManager;
  private ContactsRepository contactsRepository;
  private PhoneCalls phoneCalls;
  private String deviceId;

  @Inject
  public LocalPhoneStateListener(
    TelephonyManager telephonyManager,
    ContactsRepository contactsRepository,
    PhoneCalls phoneCalls) {
    this.telephonyManager = telephonyManager;
    this.contactsRepository = contactsRepository;
    this.phoneCalls = phoneCalls;
  }

  @Override
  public void start(String deviceId) {
    this.deviceId = deviceId;
    telephonyManager.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
  }

  @Override
  public void stop() {
    telephonyManager.listen(this, PhoneStateListener.LISTEN_NONE);
  }

  @Override
  public void onCallStateChanged(int state, String incomingNumber) {
    super.onCallStateChanged(state, incomingNumber);

    if (state == TelephonyManager.CALL_STATE_RINGING) {
      PhoneCallDto phoneCallDto = new PhoneCallDto(this.deviceId, incomingNumber, contactsRepository.findByPhoneNumber(incomingNumber));
      phoneCalls.create(phoneCallDto).subscribe();
    }
  }
}
