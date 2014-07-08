package com.omnipaste.droidomni.fragments;

import com.omnipaste.droidomni.events.DeviceInitErrorEvent;
import com.omnipaste.droidomni.events.DeviceInitEvent;
import com.omnipaste.droidomni.services.DeviceService;
import com.omnipaste.droidomni.services.DeviceServiceImpl;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DeviceInitFragmentTest extends TestCase {
  private DeviceInitFragment subject;

  private interface HandlesDeviceInitEvent {
    @SuppressWarnings("UnusedDeclaration")
    void onEvent(DeviceInitEvent deviceInitEvent);
  }

  private interface HandlesDeviceInitErrorEvent {
    @SuppressWarnings("UnusedDeclaration")
    void onEvent(DeviceInitErrorEvent deviceInitErrorEvent);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();

    subject = new DeviceInitFragment();
  }

  public void testAfterViewOnNextWillPostDeviceInitEvent() throws Exception {
    ReentrantLock lock = new ReentrantLock();
    final Condition onEventWasCalled = lock.newCondition();
    final Boolean[] wasCalled = {false};

    HandlesDeviceInitEvent handler = new HandlesDeviceInitEvent() {
      @Override
      public void onEvent(DeviceInitEvent deviceInitEvent) {
        wasCalled[0] = true;
        onEventWasCalled.signal();
      }
    };

    Observable<RegisteredDeviceDto> registeredDeviceDtoObservable = Observable.create(new Observable.OnSubscribe<RegisteredDeviceDto>() {
      @Override
      public void call(Subscriber<? super RegisteredDeviceDto> subscriber) {
        subscriber.onNext(new RegisteredDeviceDto());
        subscriber.onCompleted();
      }
    });

    prepare(handler, registeredDeviceDtoObservable);

    lock.lock();
    subject.afterViews();
    onEventWasCalled.await(1, TimeUnit.SECONDS);

    assertTrue(wasCalled[0]);
    lock.unlock();
  }

  public void testAfterViewOnErrorWillPostDeviceInitErrorEvent() throws Exception {
    ReentrantLock lock = new ReentrantLock();
    final Condition onEventWasCalled = lock.newCondition();
    final Boolean[] wasCalled = {false};

    HandlesDeviceInitErrorEvent handler = new HandlesDeviceInitErrorEvent() {
      @Override
      public void onEvent(DeviceInitErrorEvent deviceInitErrorEvent) {
        wasCalled[0] = true;
        onEventWasCalled.signal();
      }
    };

    Observable<RegisteredDeviceDto> registeredDeviceDtoObservable = Observable.create(new Observable.OnSubscribe<RegisteredDeviceDto>() {
      @Override
      public void call(Subscriber<? super RegisteredDeviceDto> subscriber) {
        subscriber.onError(new Exception());
      }
    });

    prepare(handler, registeredDeviceDtoObservable);

    lock.lock();
    subject.afterViews();
    onEventWasCalled.await(1, TimeUnit.SECONDS);

    assertTrue(wasCalled[0]);
    lock.unlock();
  }

  public void testGetDeviceServiceWhenNoDeviceServiceWasSetWillReturnANewDeviceServiceImpl() throws Exception {
    assertThat(subject.getDeviceService(), instanceOf(DeviceServiceImpl.class));
  }

  public void testGetDeviceServiceWhenSetWillReturnTheSetValue() throws Exception {
    DeviceService deviceService = new DeviceServiceImpl();
    subject.setDeviceService(deviceService);

    assertThat(subject.getDeviceService(), is(deviceService));
  }

  private void prepare(Object handler, Observable<RegisteredDeviceDto> observable) {
    EventBus.getDefault().register(handler);

    DeviceService mockDeviceService = mock(DeviceService.class);
    when(mockDeviceService.init()).thenReturn(observable);
    subject.setDeviceService(mockDeviceService);
  }
}