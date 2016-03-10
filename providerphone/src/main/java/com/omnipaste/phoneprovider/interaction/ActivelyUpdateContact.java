package com.omnipaste.phoneprovider.interaction;

import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.phoneprovider.ContactsRepository;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Func1;

@Singleton
public class ActivelyUpdateContact {

  private Contacts contacts;
  private ContactsRepository contactsRepository;

  @Inject
  public ActivelyUpdateContact(Contacts contacts, ContactsRepository contactsRepository) {
    this.contacts = contacts;
    this.contactsRepository = contactsRepository;
  }

  public ContactDto fromPhoneNumber(String phoneNumber) {
    final ContactDto contactDto = contactsRepository.findByPhoneNumber(phoneNumber);
    if (contactDto.getContactId() == null) {
      return new ContactDto();
    }

    contacts.get(contactDto.getContactId())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ContactDto>>() {
        @Override
        public Observable<? extends ContactDto> call(Throwable throwable) {
          Observable<ContactDto> result = Observable.empty();
          if (isNotFound(throwable)) {
            result = contactsRepository
              .find(contactDto.getContactId())
              .flatMap(new Func1<ContactDto, Observable<ContactDto>>() {
                @Override public Observable<ContactDto> call(ContactDto contactDto) {
                  return contacts.create(contactDto);
                }
              });
          }

          return result;
        }
      })
      .flatMap(new Func1<ContactDto, Observable<?>>() {
        @Override public Observable<?> call(final ContactDto getContactDto) {
          return contactsRepository
            .find(getContactDto.getContactId())
            .flatMap(new Func1<ContactDto, Observable<?>>() {
              @Override public Observable<?> call(ContactDto localContactDto) {
                Observable<ContactDto> result = Observable.empty();
                if (!localContactDto.equals(getContactDto)) {
                  result = contacts.update(localContactDto.setId(getContactDto.getId()));
                }

                return result;
              }
            });
        }
      })
      .subscribe();

    return contactDto;
  }

  private boolean isNotFound(Throwable throwable) {
    return throwable instanceof RetrofitError &&
      ((RetrofitError) throwable).getResponse() != null &&
      ((RetrofitError) throwable).getResponse().getStatus() == HttpURLConnection.HTTP_NOT_FOUND;
  }
}
