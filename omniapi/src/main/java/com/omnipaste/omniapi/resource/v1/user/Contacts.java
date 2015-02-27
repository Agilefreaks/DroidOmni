package com.omnipaste.omniapi.resource.v1.user;

import com.omnipaste.omniapi.dto.BatchDto;
import com.omnipaste.omniapi.dto.RequestList;
import com.omnipaste.omniapi.resource.v1.AuthorizationResource;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.ContactDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.data.List;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

@Singleton
public class Contacts extends AuthorizationResource<Contacts.ContactsApi> {
  protected interface ContactsApi {
    @POST("/v1/user/contacts.json")
    Observable<ContactDto> create(@Header("Authorization") String token, @Body ContactDto contact);

    @POST("/v1/batch")
    Observable<Object[]> create(@Header("Authorization") String token, @Body BatchDto requests);

    @GET("/v1/user/contacts")
    Observable<ContactDto> get(@Header("Authorization") String token, @Query("contact_id") Long contactId);
  }

  @Inject
  public Contacts(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, ContactsApi.class, authorizationService);
  }

  public Observable create(ContactDto[] contacts) {
    BatchDto batchDto = new BatchDto(RequestList.buildFromContacts(List.list(contacts)));
    return authorizationService.authorize(api.create(bearerAccessToken(), batchDto));
  }

  public Observable create(java.util.List<ContactDto> contacts) {
    return create(contacts.toArray(new ContactDto[contacts.size()]));
  }

  public Observable<ContactDto> create(ContactDto contactDto) {
    return authorizationService.authorize(api.create(bearerAccessToken(), contactDto));
  }

  public Observable<ContactDto> get(Long contactId) {
    return authorizationService.authorize(api.get(bearerAccessToken(), contactId));
  }
}
