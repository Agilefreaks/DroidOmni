package com.omnipaste.notificationsprovider;

import android.telephony.TelephonyManager;

import com.omnipaste.omnicommon.dto.TelephonyNotificationDto;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import rx.Observer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OmniPhoneStateListenerTest extends TestCase {
  OmniPhoneStateListener omniPhoneStateListener;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    omniPhoneStateListener = new OmniPhoneStateListener();
  }

  @SuppressWarnings("unchecked")
  public void testOnCallStateChangedForStateRinging() throws Exception {
    Observer observer = mock(Observer.class);
    omniPhoneStateListener.getObservable().subscribe(observer);

    omniPhoneStateListener.onCallStateChanged(TelephonyManager.CALL_STATE_RINGING, "42");

    ArgumentCaptor<TelephonyNotificationDto> telephonyNotificationDtoArgumentCaptor = ArgumentCaptor.forClass(TelephonyNotificationDto.class);
    verify(observer).onNext(telephonyNotificationDtoArgumentCaptor.capture());

    assertThat(telephonyNotificationDtoArgumentCaptor.getValue().getIncomingCall().getPhoneNumber(), is("42"));
    assertThat(telephonyNotificationDtoArgumentCaptor.getValue().getType(), is(TelephonyNotificationDto.TelephonyNotificationType.incomingCall));
  }
}