package com.omnipaste.eventsprovider;

import android.test.InstrumentationTestCase;

import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.eventsprovider.listeners.OmniSmsListener;
import com.omnipaste.omniapi.resource.v1.Events;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TelephonyEventsProviderTest extends InstrumentationTestCase {
  private TelephonyEventsProvider subject;
  private Events mockEvents;
  private OmniPhoneStateListener mockOmniPhoneStateListener;
  private OmniSmsListener mockOmniSmsListener;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockEvents = mock(Events.class);
    mockOmniPhoneStateListener = mock(OmniPhoneStateListener.class);
    mockOmniSmsListener = mock(OmniSmsListener.class);
    subject = new TelephonyEventsProvider(mockEvents, mockOmniPhoneStateListener, mockOmniSmsListener);
  }

  public void testInitItCallsStartOnBothListeners() throws Exception {
    subject.init("Muchen");

    verify(mockOmniPhoneStateListener).start(subject);
    verify(mockOmniSmsListener).start(subject);
  }

  public void testPostWillCallCreateOnEvents() throws Exception {
    TelephonyEventDto telephonyEventDto = new TelephonyEventDto();
    when(mockEvents.create(telephonyEventDto)).thenReturn(Observable.<TelephonyEventDto>empty());

    subject.post(telephonyEventDto);

    verify(mockEvents).create(telephonyEventDto);
  }
}