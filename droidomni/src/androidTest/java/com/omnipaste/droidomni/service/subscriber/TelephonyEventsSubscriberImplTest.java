package com.omnipaste.droidomni.service.subscriber;

import android.test.InstrumentationTestCase;

import com.omnipaste.eventsprovider.TelephonyEventsProvider;
import com.omnipaste.omnicommon.dto.NotificationDto;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TelephonyEventsSubscriberImplTest extends InstrumentationTestCase {
  @Mock
  public TelephonyEventsProvider telephonyEventsProvider;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);
  }

  public void testStartWillCallInitWithTheDeviceIdentifier() throws Exception {
    TelephonyNotificationsSubscriber subscriber = new TelephonyNotificationsSubscriber(telephonyEventsProvider);
    when(telephonyEventsProvider.init(any(String.class))).thenReturn(Observable.create(new Observable.OnSubscribe<NotificationDto>() {
      @Override
      public void call(Subscriber<? super NotificationDto> subscriber) {
      }
    }));

    subscriber.start("42");

    verify(telephonyEventsProvider, times(1)).init("42");
  }
}