package com.omnipaste.eventsprovider;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.eventsprovider.listeners.EventsReceiver;
import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OmniPhoneStateListenerTest extends InstrumentationTestCase {
  private OmniPhoneStateListener subject;
  private TelephonyManager mockTelephonyManager;
  private EventsReceiver mockEventsReceiver;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockTelephonyManager = mock(TelephonyManager.class);
    mockEventsReceiver = mock(EventsReceiver.class);
    subject = new OmniPhoneStateListener(mockTelephonyManager);
  }

  public void testStartWillCallListenOnTelephonyManager() throws Exception {
    subject.start(mockEventsReceiver);

    verify(mockTelephonyManager).listen(subject, PhoneStateListener.LISTEN_CALL_STATE);
  }

  public void testStopWillCallUnlistenToTelephonyManager() throws Exception {
    subject.stop();

    verify(mockTelephonyManager).listen(subject, PhoneStateListener.LISTEN_NONE);

  }
}