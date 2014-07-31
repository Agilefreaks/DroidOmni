package com.omnipaste.clipboardprovider.omniclipboard;

import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import java.util.Arrays;

import rx.Observable;
import rx.Observer;
import rx.observers.TestObserver;
import rx.schedulers.TestScheduler;
import rx.subjects.TestSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OmniClipboardManagerTest extends InstrumentationTestCase {
  private final TestScheduler testScheduler = new TestScheduler();
  private final TestSubject<ClippingDto> lastObservable = TestSubject.create(testScheduler);
  private final TestSubject<NotificationDto> notificationSubject = TestSubject.create(testScheduler);

  private OmniClipboardManager omniClipboardManager;
  private OmniApi mockOmniApi;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    NotificationProvider notificationProvider = mock(NotificationProvider.class);
    when(notificationProvider.getObservable()).thenReturn(notificationSubject);

    mockOmniApi = mock(OmniApi.class);
    Clippings mockClippings = mock(Clippings.class);
    when(mockOmniApi.clippings()).thenReturn(mockClippings);
    when(mockClippings.last()).thenReturn(lastObservable);

    omniClipboardManager = new OmniClipboardManager(notificationProvider);
    omniClipboardManager.omniApi = mockOmniApi;
  }

  public void testGetObservableWillReturnTheCorrectType() throws Exception {
    assertThat(omniClipboardManager.getObservable(), isA(Observable.class));
  }

  @SuppressWarnings("unchecked")
  public void testWillEmitEventsWhenProviderIsClipboard() {
    Observer observer = mock(Observer.class);
    ClippingDto clippingDto = new ClippingDto();
    omniClipboardManager.getObservable().subscribe(observer);

    notificationSubject.onNext(new NotificationDto(NotificationDto.Target.CLIPBOARD, "42"));
    lastObservable.onNext(clippingDto);
    testScheduler.triggerActions();

    verify(observer, times(1)).onNext(clippingDto);
  }

  @SuppressWarnings("unchecked")
  public void testWillNotEmitEventsWhenProviderIsNotClipboard() {
    Observer observer = mock(Observer.class);
    omniClipboardManager.getObservable().subscribe(observer);

    notificationSubject.onNext(new NotificationDto(NotificationDto.Target.PHONE, "42"));
    lastObservable.onNext(new ClippingDto());
    testScheduler.triggerActions();

    verify(observer, never()).onNext(any(ClippingDto.class));
  }

  public void testGetObservableWillReturnTheSameInstanceOnMultipleCalls() throws Exception {
    assertThat(omniClipboardManager.getObservable(), sameInstance(omniClipboardManager.getObservable()));
  }

  public void testOnPrimaryChangedWillGetPrimaryAgain() throws Exception {
    TestObserver<ClippingDto> testObserver = new TestObserver<>();
    ClippingDto clippingDto = new ClippingDto();
    omniClipboardManager.getObservable().subscribe(testObserver);

    omniClipboardManager.onPrimaryClipChanged();
    lastObservable.onNext(clippingDto);
    testScheduler.triggerActions();

    testObserver.assertReceivedOnNext(Arrays.asList(clippingDto));
  }
}
