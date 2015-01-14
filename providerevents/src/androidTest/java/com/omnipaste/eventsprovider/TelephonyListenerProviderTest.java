package com.omnipaste.eventsprovider;

import android.test.InstrumentationTestCase;

import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.eventsprovider.listeners.OmniSmsListener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TelephonyListenerProviderTest extends InstrumentationTestCase {
  private TelephonyListenerProvider subject;
  private OmniPhoneStateListener mockOmniPhoneStateListener;
  private OmniSmsListener mockOmniSmsListener;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockOmniPhoneStateListener = mock(OmniPhoneStateListener.class);
    mockOmniSmsListener = mock(OmniSmsListener.class);
    subject = new TelephonyListenerProvider(mockOmniPhoneStateListener, mockOmniSmsListener);
  }

  public void testInitItCallsStartOnBothListeners() throws Exception {
    subject.init("Muchen");

    verify(mockOmniPhoneStateListener).start("Muchen");
    verify(mockOmniSmsListener).start("Muchen");
  }
}