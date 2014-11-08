package com.omnipaste.phoneprovider;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.actions.Action;

import rx.subjects.BehaviorSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PhoneProviderTest extends InstrumentationTestCase {
  private PhoneProvider phoneProvider;
  private BehaviorSubject<NotificationDto> notificationSubject = BehaviorSubject.create(new NotificationDto());

  public ActionFactory mockActionFactory;

  @Override
  @SuppressWarnings("ConstantConditions")
  public void setUp() throws Exception {
    super.setUp();

    mockActionFactory = mock(ActionFactory.class);
    NotificationProvider notificationProvider = mock(NotificationProvider.class);
    when(notificationProvider.getObservable()).thenReturn(notificationSubject);

    phoneProvider = new PhoneProvider(notificationProvider, mockActionFactory);
  }

  public void testGetObservableDoesNotSubscribeToClipboardNotifications() throws Exception {
    phoneProvider.init("smart watch");

    notificationSubject.onNext(new NotificationDto(NotificationDto.Target.CLIPBOARD, "42"));

    verify(mockActionFactory, never()).create(any(PhoneAction.class));
  }

  public void testGetObservableSubscribesToPhoneNotificationTargetOnly() throws Exception {
    Action callAction = mock(Action.class);
    phoneProvider.init("smart watch");

    when(mockActionFactory.create(PhoneAction.CALL)).thenReturn(callAction);
    Bundle extra = new Bundle();
    extra.putString("phone_number", "123");
    extra.putString("phone_action", "call");
    notificationSubject.onNext(new NotificationDto(NotificationDto.Target.PHONE, "42", extra));

    verify(callAction, times(1)).execute(extra);
  }
}