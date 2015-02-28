package com.omnipaste.phoneprovider.listeners;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.phoneprovider.ContactsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhoneStateListener extends android.telephony.PhoneStateListener implements Listener {
  private TelephonyManager telephonyManager;
  private ContactsRepository contactsRepository;
  private PhoneCalls phoneCalls;
  private String deviceId;

  @Inject
  public PhoneStateListener(
    TelephonyManager telephonyManager,
    ContactsRepository contactsRepository,
    PhoneCalls phoneCalls) {
    this.telephonyManager = telephonyManager;
    this.contactsRepository = contactsRepository;
    this.phoneCalls = phoneCalls;
  }

  @Override
  public void start(Context context, String deviceId) {
    this.deviceId = deviceId;
    telephonyManager.listen(this, android.telephony.PhoneStateListener.LISTEN_CALL_STATE);
  }

  @Override
  public void stop() {
    telephonyManager.listen(this, android.telephony.PhoneStateListener.LISTEN_NONE);
  }

  @Override
  public void onCallStateChanged(int state, String incomingNumber) {
    super.onCallStateChanged(state, incomingNumber);
    if (state != TelephonyManager.CALL_STATE_RINGING) {
      return;
    }

    PhoneCallDto phoneCallDto = new PhoneCallDto().setDeviceId(deviceId);
    final ContactDto contactDto = contactsRepository.findByPhoneNumber(incomingNumber);

    if (contactDto != null) {
      phoneCallDto
        .setContactId(contactDto.getContactId())
        .setContactName(contactDto.getName());
    }

    phoneCalls.create(phoneCallDto).subscribe();
  }
}
