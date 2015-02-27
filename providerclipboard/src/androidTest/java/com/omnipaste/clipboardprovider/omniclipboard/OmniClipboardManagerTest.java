package com.omnipaste.clipboardprovider.omniclipboard;

import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.resource.v1.Clippings;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import java.util.Arrays;

import rx.Observable;
import rx.observers.TestObserver;
import rx.schedulers.TestScheduler;
import rx.subjects.TestSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OmniClipboardManagerTest extends InstrumentationTestCase {
  private final TestScheduler testScheduler = new TestScheduler();
  private final TestSubject<ClippingDto> lastObservable = TestSubject.create(testScheduler);
  private final TestSubject<NotificationDto> notificationSubject = TestSubject.create(testScheduler);

  private OmniClipboardManager subject;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    NotificationProvider notificationProvider = mock(NotificationProvider.class);
    when(notificationProvider.getObservable()).thenReturn(notificationSubject);

    subject = new OmniClipboardManager(notificationProvider, mock(Clippings.class));
  }

  public void testGetObservableWillReturnTheCorrectType() throws Exception {
    assertThat(subject.getObservable(), isA(Observable.class));
  }

  public void testGetObservableWillReturnTheSameInstanceOnMultipleCalls() throws Exception {
    assertThat(subject.getObservable(), sameInstance(subject.getObservable()));
  }

  public void testOnPrimaryChangedWillGetPrimaryAgain() throws Exception {
    TestObserver<ClippingDto> testObserver = new TestObserver<>();
    ClippingDto clippingDto = new ClippingDto();
    subject.getObservable().subscribe(testObserver);

    subject.onPrimaryClipChanged("42");
    lastObservable.onNext(clippingDto);
    testScheduler.triggerActions();

    testObserver.assertReceivedOnNext(Arrays.asList(clippingDto));
  }
}
