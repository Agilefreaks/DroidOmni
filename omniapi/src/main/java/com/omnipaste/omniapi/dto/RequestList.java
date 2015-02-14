package com.omnipaste.omniapi.dto;

import com.omnipaste.omnicommon.dto.ContactDto;

import java.util.ArrayList;

import fj.F;
import fj.data.Array;
import fj.data.List;

public class RequestList<T> extends ArrayList<RequestDto<T>> {
  public static RequestList<ContactDto> buildFromContacts(List<ContactDto> contacts) {
    List<RequestDto<ContactDto>> result = contacts.map(new F<ContactDto, RequestDto<ContactDto>>() {
      @Override
      public RequestDto<ContactDto> f(ContactDto contactDto) {
        return new RequestDto<ContactDto>()
          .setMethod("POST")
          .setPath("/api/v1/user/contacts")
          .setBody(contactDto);
      }
    });

    return new RequestList<>(result.toArray());
  }

  protected RequestList(Array<RequestDto<T>> requests) {
    super();

    for(RequestDto<T> requestDto: requests) {
      add(requestDto);
    }
  }
}
