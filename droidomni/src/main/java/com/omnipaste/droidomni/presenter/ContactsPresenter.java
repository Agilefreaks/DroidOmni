package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.service.ContactsService;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

@Singleton
public class ContactsPresenter extends Presenter<ContactsPresenter.View> implements Observer<ContactSyncNotification>  {
  private final ContactsService contactsService;
  private final PublishSubject<ContactSyncNotification> contactsSubject = PublishSubject.create();
  private Subscription subscription;

  public interface View {
  }

  @Inject
  public ContactsPresenter(ContactsService contactsService) {
    this.contactsService = contactsService;
  }

  @Override
  public void initialize() {
    if (subscription != null) {
      return;
    }

    subscription = contactsService
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
    contactsSubject.onCompleted();

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
  public void onNext(ContactSyncNotification contactSyncNotification) {
    contactsSubject.onNext(contactSyncNotification);
  }

  public Observable<ContactSyncNotification> getObservable() {
    return contactsSubject;
  }
}
