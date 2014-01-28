package com.omnipaste.clipboardprovider.omniclipboard;

import com.omnipaste.omnicommon.Target;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import junit.framework.TestCase;

import rx.Observable;
import rx.Observer;
import rx.subjects.BehaviorSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OmniClipboardManagerTest extends TestCase {
  private OmniClipboardManager omniClipboardManager;
  private BehaviorSubject<NotificationDto> notificationSubject = BehaviorSubject.create(new NotificationDto());

  @Override
  public void setUp() throws Exception {
    super.setUp();

    NotificationProvider notificationProvider = mock(NotificationProvider.class);
    when(notificationProvider.getObservable()).thenReturn(notificationSubject);

    omniClipboardManager = new OmniClipboardManager(notificationProvider);
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
}
