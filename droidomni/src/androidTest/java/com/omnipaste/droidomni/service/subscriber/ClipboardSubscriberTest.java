package com.omnipaste.droidomni.service.subscriber;

import android.test.InstrumentationTestCase;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClipboardSubscriberTest extends InstrumentationTestCase {
  @Mock
  public ClipboardProvider clipboardProvider;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);
  }

  public void testStartWillCallInitWithTheDeviceIdentifier() throws Exception {
    ClipboardSubscriber subscriber = new ClipboardSubscriber(clipboardProvider);
    when(clipboardProvider.init(any(String.class))).thenReturn(Observable.create(new Observable.OnSubscribe<ClippingDto>() {
      @Override
      public void call(Subscriber<? super ClippingDto> subscriber) {
        // empty
      }
    }));

    subscriber.start("42");

    verify(clipboardProvider, times(1)).init("42");
  }
}