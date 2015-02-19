package com.omnipaste.phoneprovider.listeners;

import android.telephony.TelephonyManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.phoneprovider.ContactsRepository;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PhoneStateListenerTest extends InstrumentationTestCase {
  private PhoneStateListener phoneStateListener;

  @Mock public ContactsRepository mockContactsRepository;
  @Mock public PhoneCalls mockPhoneCalls;
  @Mock public TelephonyManager mockTelephonyManager;

  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    phoneStateListener = new PhoneStateListener(
      mockTelephonyManager,
      mockContactsRepository,
      mockPhoneCalls);
  }

  public void testOnCallStateChanged() {
    when(mockContactsRepository.findByPhoneNumber("123")).thenReturn(new ContactDto());

    phoneStateListener.onCallStateChanged(TelephonyManager.CALL_STATE_RINGING, "42");

    verify(mockPhoneCalls).create(any(PhoneCallDto.class));
  }
}