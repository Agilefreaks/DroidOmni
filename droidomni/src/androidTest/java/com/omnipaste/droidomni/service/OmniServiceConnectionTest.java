package com.omnipaste.droidomni.service;

import android.content.Context;
import android.test.InstrumentationTestCase;

import java.util.concurrent.TimeUnit;

import rx.functions.Action0;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OmniServiceConnectionTest extends InstrumentationTestCase {
  private OmniServiceConnection subject;
  private SessionService mockSessionService;
  private PublishSubject<OmniServiceConnection.State> serviceStateObservable;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockSessionService = mock(SessionService.class);
    serviceStateObservable = PublishSubject.create();
    subject = new OmniServiceConnection(mock(Context.class), mockSessionService, serviceStateObservable);
  }

  public void testExitWhenNotTimeoutWillCleanupAndCallAction() throws Exception {
    TestScheduler testScheduler = new TestScheduler();
    subject.setObserveOnScheduler(testScheduler);
    Action0 mockAction0 = mock(Action0.class);

    subject.stopOmniService(mockAction0);

    testScheduler.advanceTimeBy(499, TimeUnit.MICROSECONDS);
    serviceStateObservable.onNext(OmniServiceConnection.State.stopped);
    testScheduler.triggerActions();

    verify(mockSessionService).setRegisteredDeviceDto(null);
    verify(mockAction0).call();
  }

  public void testExitWhenTimeoutWillStillCleanupAndCallAction() throws Exception {
    TestScheduler testScheduler = new TestScheduler();
    subject.setObserveOnScheduler(testScheduler);
    Action0 mockAction0 = mock(Action0.class);

    subject.stopOmniService(mockAction0);

    testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
    serviceStateObservable.onNext(OmniServiceConnection.State.stopped);
    testScheduler.triggerActions();

    verify(mockSessionService).setRegisteredDeviceDto(null);
    verify(mockAction0).call();
  }
}