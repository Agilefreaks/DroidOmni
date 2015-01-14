package com.omnipaste.phoneprovider;

import android.telephony.TelephonyManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.phoneprovider.listeners.OmniPhoneStateListener;

import static org.mockito.Mockito.mock;

public class OmniPhoneStateListenerTest extends InstrumentationTestCase {
  private OmniPhoneStateListener subject;
  private TelephonyManager mockTelephonyManager;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockTelephonyManager = mock(TelephonyManager.class);
  }
}