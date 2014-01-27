package com.omnipaste.droidomni.providers;

import com.omnipaste.droidomni.events.GcmNotificationReceived;
import com.omnipaste.omnicommon.dto.NotificationDto;

import junit.framework.TestCase;

import org.hamcrest.core.IsInstanceOf;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GcmNotificationProviderTest extends TestCase {
  private GcmNotificationProvider notificationProvider;
  private EventBus eventBus = EventBus.getDefault();

  @Override
  public void setUp() throws Exception {
    super.setUp();

    notificationProvider = new GcmNotificationProvider();
  }

  public void testRegistersToEventBus() {
    assertThat(eventBus.isRegistered(notificationProvider), is(true));
  }

  public void testGetObservableReturnsAObservable() {
    assertThat(notificationProvider.getObservable(), IsInstanceOf.any(Observable.class));
  }

  @SuppressWarnings("unchecked")
  public void testOnEventBackgroundThreadShouldEmitTwoEvent() throws InterruptedException {
    Observer observer = mock(Observer.class);
    notificationProvider.getObservable().subscribe(observer);

    notificationProvider.onEventBackgroundThread(new GcmNotificationReceived());

    // first time is the default value
    verify(observer, times(1)).onNext(isA(NotificationDto.class));
  }
}
