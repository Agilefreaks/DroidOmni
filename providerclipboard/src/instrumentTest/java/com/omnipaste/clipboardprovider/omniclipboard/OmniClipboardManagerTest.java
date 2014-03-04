package com.omnipaste.clipboardprovider.omniclipboard;

import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.IOmniApi;
import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omnicommon.Target;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import rx.Observable;
import rx.Observer;
import rx.subjects.BehaviorSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OmniClipboardManagerTest extends InstrumentationTestCase {
  private OmniClipboardManager omniClipboardManager;
  private BehaviorSubject<NotificationDto> notificationSubject = BehaviorSubject.create(new NotificationDto());
  private IOmniApi mockOmniApi;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    mockOmniApi = mock(IOmniApi.class);
    NotificationProvider notificationProvider = mock(NotificationProvider.class);
    when(notificationProvider.getObservable()).thenReturn(notificationSubject);

    omniClipboardManager = new OmniClipboardManager(notificationProvider);
    omniClipboardManager.omniApi = mockOmniApi;
  }

  public void testGetObservableWillReturnTheCorrectType() throws Exception {
    assertThat(omniClipboardManager.getObservable(), isA(Observable.class));
  }

  @SuppressWarnings("unchecked")
  public void testWillEmitEventsWithRegistrationIdWhenProviderIsClipboard() {
    Observer observer = mock(Observer.class);
    omniClipboardManager.getObservable().subscribe(observer);

    notificationSubject.onNext(new NotificationDto(Target.clipboard, "42"));

    verify(observer).onNext("42");
  }

  @SuppressWarnings("unchecked")
  public void testWillNotEmitEventsWhenProviderIsNotClipboard() {
    Observer observer = mock(Observer.class);
    omniClipboardManager.getObservable().subscribe(observer);

    notificationSubject.onNext(new NotificationDto(Target.phone, "42"));

    verify(observer, never()).onNext("42");
  }

  public void testGetObservableWillReturnTheSameInstanceOnMultipleCalls() throws Exception {
    assertThat(omniClipboardManager.getObservable(), sameInstance(omniClipboardManager.getObservable()));
  }

  @SuppressWarnings("unchecked")
  public void testSetPrimaryWillReturnAClippingDtoWithTheRightProvider() throws Exception {
    Clippings clippings = mock(Clippings.class);

    when(clippings.create(eq("test@test.com"), any(ClippingDto.class))).thenReturn(mock(Observable.class));
    when(mockOmniApi.clippings()).thenReturn(clippings);

    ClippingDto clippingDto = omniClipboardManager.setPrimaryClip("test@test.com", new ClippingDto());

    assertThat(clippingDto.getClippingProvider(), is(ClippingDto.ClippingProvider.local));
  }
}
