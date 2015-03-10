package com.omnipaste.phoneprovider.listeners;

import android.telephony.TelephonyManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.phoneprovider.interaction.ActivelyUpdateContact;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PhoneStateListenerTest extends InstrumentationTestCase {
  private PhoneStateListener phoneStateListener;

  public ActivelyUpdateContact mockActivelyUpdateContact;
  public PhoneCalls mockPhoneCalls;
  public TelephonyManager mockTelephonyManager;

  public void setUp() throws Exception {
    super.setUp();

    mockActivelyUpdateContact = mock(ActivelyUpdateContact.class);
    mockPhoneCalls = mock(PhoneCalls.class);
    mockTelephonyManager = mock(TelephonyManager.class);

    phoneStateListener = new PhoneStateListener(
      mockTelephonyManager,
      mockActivelyUpdateContact,
      mockPhoneCalls);
  }

  public void testOnCallStateChanged() {
    when(mockActivelyUpdateContact.fromPhoneNumber("42")).thenReturn(new ContactDto());
    when(mockPhoneCalls.create(any(PhoneCallDto.class))).thenReturn(Observable.<PhoneCallDto>empty());

    phoneStateListener.onCallStateChanged(TelephonyManager.CALL_STATE_RINGING, "42");

    verify(mockPhoneCalls).create(any(PhoneCallDto.class));
  }
}