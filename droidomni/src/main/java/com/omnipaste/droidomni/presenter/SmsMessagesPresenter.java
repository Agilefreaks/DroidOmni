package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.SmsMessage;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.phoneprovider.listeners.OmniSmsListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

@Singleton
public class SmsMessagesPresenter extends Presenter<SmsMessagesPresenter.View> implements Observer<SmsMessageDto> {
  private final OmniSmsListener omniSmsListener;
  private PublishSubject<SmsMessage> subject;
  private Subscription subscription;

  public interface View {
  }

  @Inject
  public SmsMessagesPresenter(OmniSmsListener omniSmsListener) {
    this.omniSmsListener = omniSmsListener;
  }

  @Override
  public void initialize() {
    if (subscription != null) {
      return;
    }

    subject = PublishSubject.create();
    subscription = omniSmsListener
      .getObservable()
      .observeOn(observeOnScheduler)
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
    subject.onCompleted();

    subscription.unsubscribe();
    subscription = null;
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
