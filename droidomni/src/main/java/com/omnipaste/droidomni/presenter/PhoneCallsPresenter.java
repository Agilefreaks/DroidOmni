package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.PhoneCall;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.phoneprovider.receivers.PhoneCallReceived;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

@Singleton
public class PhoneCallsPresenter extends Presenter<SmsMessagesPresenter.View> implements Observer<PhoneCallDto> {
  private final PhoneCallReceived phoneCallReceived;
  private PublishSubject<PhoneCall> subject;
  private Subscription subscription;

  public interface View {
  }

  @Inject
  public PhoneCallsPresenter(PhoneCallReceived phoneCallReceived) {
    this.phoneCallReceived = phoneCallReceived;
  }

  @Override
  public void initialize() {
    if (subscription != null) {
      return;
    }

    subject = PublishSubject.create();
    subscription = phoneCallReceived
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
  public void onNext(PhoneCallDto phoneCallDto) {
    subject.onNext(PhoneCall.add(phoneCallDto));
  }

  public Observable<PhoneCall> getObservable() {
    return subject;
  }

  public void remove(PhoneCallDto phoneCallDto) {
    subject.onNext(PhoneCall.remove(phoneCallDto));
  }
}
