package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.ContactSyncNotification;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;

@Singleton
public class ContactsPresenter extends Presenter<ContactsPresenter.View> implements Observer<ContactSyncNotification>  {
  private final PublishSubject<ContactSyncNotification> contactsSubject = PublishSubject.create();

  public interface View {
  }

  @Inject
  public ContactsPresenter() {
  }

  @Override
  public void initialize() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    contactsSubject.onCompleted();
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(ContactSyncNotification contactSyncNotification) {
    contactsSubject.onNext(contactSyncNotification);
  }

  public Observable<ContactSyncNotification> getObservable() {
    return contactsSubject;
  }
}
