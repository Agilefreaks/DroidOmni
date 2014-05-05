package com.omnipaste.phoneprovider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.Target;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.subjects.BehaviorSubject;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AndroidPhoneProviderTest extends InstrumentationTestCase {
  private AndroidPhoneProvider androidPhoneProvider;
  private BehaviorSubject<NotificationDto> notificationSubject = BehaviorSubject.create(new NotificationDto());

  @Mock
  public Context context;

  @Override
  @SuppressWarnings("ConstantConditions")
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);

    NotificationProvider notificationProvider = mock(NotificationProvider.class);
    when(notificationProvider.getObservable()).thenReturn(notificationSubject);

    androidPhoneProvider = new AndroidPhoneProvider(notificationProvider, context);
  }

  public void testGetObservableSubscribesToPhoneNotificationTargetOnly() throws Exception {
    androidPhoneProvider.init("smart watch");

    notificationSubject.onNext(new NotificationDto(Target.clipboard, "42"));
    Bundle extra = new Bundle();
    extra.putString("phone_number", "123");
    notificationSubject.onNext(new NotificationDto(Target.phone, "42", extra));

    verify(context, times(1)).startActivity(isA(Intent.class));
  }
}
