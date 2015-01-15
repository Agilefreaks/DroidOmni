package com.omnipaste.omniapi.resource.v1.user;

import com.omnipaste.omniapi.resource.v1.AuthorizationResource;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.ContactListDto;
import com.omnipaste.omnicommon.dto.EmptyDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

@Singleton
public class Contacts extends AuthorizationResource<Contacts.ContactsApi> {
  protected interface ContactsApi {
    @POST("/v1/user/contacts.json")
    Observable<EmptyDto> create(@Header("Authorization") String token, @Body ContactListDto contactList);
  }

  @Inject
  public Contacts(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, ContactsApi.class, authorizationService);
  }

  public Observable<EmptyDto> create(String identifier, String destinationIdentifier, String contacts) {
    return authorizationService.authorize(api.create(bearerAccessToken(), new ContactListDto(identifier, destinationIdentifier, contacts)));
  }
}
