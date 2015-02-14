package com.omnipaste.omniapi.resource.v1.user;

import com.omnipaste.omniapi.dto.BatchDto;
import com.omnipaste.omniapi.dto.RequestList;
import com.omnipaste.omniapi.resource.v1.AuthorizationResource;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.ContactDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.Unit;
import fj.data.List;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

@Singleton
public class Contacts extends AuthorizationResource<Contacts.ContactsApi> {
  protected interface ContactsApi {
    @POST("/v1/user/contacts.json")
    Observable<Unit> create(@Header("Authorization") String token, @Body ContactDto contact);

    @POST("/v1/batch")
    Observable<Unit> create(@Header("Authorization") String token, @Body BatchDto requests);
  }

  @Inject
  public Contacts(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, ContactsApi.class, authorizationService);
  }

  public Observable<Unit> create(ContactDto[] contacts) {
    BatchDto batchDto = new BatchDto(RequestList.buildFromContacts(List.list(contacts)));
    return authorizationService.authorize(api.create(bearerAccessToken(), batchDto));
  }

  public Observable<Unit> create(java.util.List<ContactDto> contacts) {
    return create(contacts.toArray(new ContactDto[contacts.size()]));
  }
}
