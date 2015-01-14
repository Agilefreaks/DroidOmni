package com.omnipaste.phoneprovider;

import android.test.InstrumentationTestCase;

import com.omnipaste.phoneprovider.listeners.LocalPhoneStateListener;
import com.omnipaste.phoneprovider.listeners.LocalSmsListener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TelephonyProviderFacadeTest extends InstrumentationTestCase {
  private TelephonyProviderFacade subject;
  private LocalPhoneStateListener mockLocalPhoneStateListener;
  private LocalSmsListener mockLocalSmsListener;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockLocalPhoneStateListener = mock(LocalPhoneStateListener.class);
    mockLocalSmsListener = mock(LocalSmsListener.class);
    subject = new TelephonyProviderFacade(mockLocalPhoneStateListener, mockLocalSmsListener);
  }

  public void testInitItCallsStartOnBothListeners() throws Exception {
    subject.init("Muchen");

    verify(mockLocalPhoneStateListener).start("Muchen");
    verify(mockLocalSmsListener).start("Muchen");
  }
}