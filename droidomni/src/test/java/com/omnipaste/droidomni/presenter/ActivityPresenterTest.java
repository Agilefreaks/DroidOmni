package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.Clipping;
import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.domain.PhoneCall;
import com.omnipaste.droidomni.domain.SmsMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityPresenterTest {
  private ActivityPresenter activityPresenter;
  private PublishSubject<Clipping> clippingsObservable = PublishSubject.create();
  private PublishSubject<PhoneCall> phoneCallObservable = PublishSubject.create();
  private PublishSubject<SmsMessage> smsMessageObservable = PublishSubject.create();
  private PublishSubject<ContactSyncNotification> contactsObservable = PublishSubject.create();

  @Mock public ClippingsPresenter mockClippingPresenter;
  @Mock public SmsMessagesPresenter mockSmsMessagesPresenter;
  @Mock public PhoneCallsPresenter mockPhoneCallsPresenter;
  @Mock public ContactsPresenter mockContactsPresenter;

  @Before
  public void context() {
    activityPresenter = new ActivityPresenter(mockClippingPresenter, mockSmsMessagesPresenter, mockPhoneCallsPresenter, mockContactsPresenter);
    activityPresenter.setObserveOnScheduler(Schedulers.immediate());

    when(mockClippingPresenter.getObservable()).thenReturn(clippingsObservable);
    when(mockPhoneCallsPresenter.getObservable()).thenReturn(phoneCallObservable);
    when(mockSmsMessagesPresenter.getObservable()).thenReturn(smsMessageObservable);
    when(mockContactsPresenter.getObservable()).thenReturn(contactsObservable);
  }

  @Test
  public void initializeWillCallInitializeForAllPresenters() {
    activityPresenter.initialize();

    verify(mockClippingPresenter).initialize();
    verify(mockSmsMessagesPresenter).initialize();
    verify(mockPhoneCallsPresenter).initialize();
    verify(mockContactsPresenter).initialize();
  }

  @Test
  public void initializeWillCallShowSampleOnClippingsPresenter() {
    activityPresenter.initialize();

    verify(mockClippingPresenter).showSamples();
  }
}