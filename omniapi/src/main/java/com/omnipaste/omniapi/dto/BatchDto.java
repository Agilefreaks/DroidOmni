package com.omnipaste.omniapi.dto;

public class BatchDto {
  private RequestList requests;

  public BatchDto(RequestList requests) {
    this.requests = requests;
  }

  public RequestList getRequests() {
    return requests;
  }
}
