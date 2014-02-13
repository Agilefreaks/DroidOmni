package com.omnipaste.phoneprovider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.Target;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import rx.subjects.BehaviorSubject;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PhoneProviderTest extends InstrumentationTestCase {
  private PhoneProvider phoneProvider;
  private BehaviorSubject<NotificationDto> notificationSubject = BehaviorSubject.create(new NotificationDto());

  @Override
  @SuppressWarnings("ConstantConditions")
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    NotificationProvider notificationProvider = mock(NotificationProvider.class);
    when(notificationProvider.getObservable()).thenReturn(notificationSubject);

    phoneProvider = new PhoneProvider(notificationProvider);
  }

  public void testGetObservableSubscribesToPhoneNotificationTargetOnly() throws Exception {
    Context context = mock(Context.class);
    phoneProvider.subscribe(context);

    notificationSubject.onNext(new NotificationDto(Target.clipboard, "42"));
    Bundle extra = new Bundle();
    extra.putString("phone_number", "123");
    notificationSubject.onNext(new NotificationDto(Target.phone, "42", extra));

    verify(context, times(1)).startActivity(isA(Intent.class));
  }
}
