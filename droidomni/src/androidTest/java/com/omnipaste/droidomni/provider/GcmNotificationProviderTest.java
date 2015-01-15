package com.omnipaste.droidomni.provider;

import com.omnipaste.omnicommon.dto.NotificationDto;

import junit.framework.TestCase;

import org.hamcrest.core.IsInstanceOf;

import rx.Observable;
import rx.Observer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GcmNotificationProviderTest extends TestCase {
  private GcmNotificationProvider notificationProvider;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    notificationProvider = new GcmNotificationProvider();
  }

  public void testGetObservableReturnsAObservable() {
    assertThat(notificationProvider.getObservable(), IsInstanceOf.any(Observable.class));
  }

  @SuppressWarnings("unchecked")
  public void testOnEventBackgroundThreadShouldEmitTwoEvent() throws InterruptedException {
    Observer observer = mock(Observer.class);
    notificationProvider.getObservable().subscribe(observer);

    notificationProvider.post(new NotificationDto(NotificationDto.Type.CLIPPING_CREATED, "123"));

    verify(observer, times(1)).onNext(isA(NotificationDto.class));
  }
}
