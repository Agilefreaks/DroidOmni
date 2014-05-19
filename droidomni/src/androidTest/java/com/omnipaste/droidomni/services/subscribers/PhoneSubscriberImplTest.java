package com.omnipaste.droidomni.services.subscribers;

import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.phoneprovider.PhoneProvider;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PhoneSubscriberImplTest extends InstrumentationTestCase {
  @Mock
  public PhoneProvider phoneProvider;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);
  }

  public void testStartWillCallInitWithTheDeviceIdentifier() throws Exception {
    PhoneSubscriberImpl subscriber = new PhoneSubscriberImpl(phoneProvider);
    when(phoneProvider.init(any(String.class))).thenReturn(Observable.create(new Observable.OnSubscribe<EmptyDto>() {
      @Override
      public void call(Subscriber<? super EmptyDto> subscriber) {
        // empty
      }
    }));

    subscriber.start("42");

    verify(phoneProvider, times(1)).init("42");
  }
}