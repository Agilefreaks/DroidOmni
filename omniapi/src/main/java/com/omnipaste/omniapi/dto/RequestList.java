package com.omnipaste.omniapi.dto;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omnipaste.omnicommon.dto.ContactDto;

import java.util.ArrayList;
import java.util.Arrays;

import fj.F;
import fj.data.Array;
import fj.data.List;

public class RequestList extends ArrayList<RequestDto> {
  public static RequestList buildFromContacts(List<ContactDto> contacts) {
    List<RequestDto> result = contacts.map(new F<ContactDto, RequestDto>() {
      @Override
      public RequestDto f(ContactDto contactDto) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        String json = gson.toJson(contactDto);
        return new RequestDto().setMethod("POST").setPath("/api/v1/user/contacts").setBody(json);
      }
    });

    return new RequestList(result.toArray());
  }

  protected RequestList(Array<RequestDto> requests) {
    super(Arrays.asList(requests.array(RequestDto[].class)));
  }
}
