package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.SmsMessage;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.phoneprovider.receivers.SmsMessageReceived;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

@Singleton
public class SmsMessagesPresenter extends Presenter<SmsMessagesPresenter.View> implements Observer<SmsMessageDto> {
  private final SmsMessageReceived smsMessageReceived;
  private PublishSubject<SmsMessage> subject;
  private Subscription subscription;

  public interface View {
  }

  @Inject
  public SmsMessagesPresenter(SmsMessageReceived smsMessageReceived) {
    this.smsMessageReceived = smsMessageReceived;
  }

  @Override
  public void initialize() {
    if (subscription != null) {
      return;
    }

    subject = PublishSubject.create();
    subscription = smsMessageReceived
      .getObservable()
      .observeOn(getObserveOnScheduler())
      .subscribe(this);
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    /* we need a way to persist the info
    subject.onCompleted();

    subscription.unsubscribe();
    subscription = null;*/
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(SmsMessageDto smsMessageDto) {
    subject.onNext(SmsMessage.add(smsMessageDto));
  }

  public Observable<SmsMessage> getObservable() {
    return subject;
  }

  public void remove(SmsMessageDto smsMessageDto) {
    subject.onNext(SmsMessage.remove(smsMessageDto));
  }
}
