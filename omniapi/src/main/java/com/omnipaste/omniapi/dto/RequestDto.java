package com.omnipaste.omniapi.dto;

public class RequestDto {
  private String method, path, body;

  public String getMethod() {
    return method;
  }

  public RequestDto setMethod(String method) {
    this.method = method;
    return this;
  }

  public String getPath() {
    return path;
  }

  public RequestDto setPath(String path) {
    this.path = path;
    return this;
  }

  public String getBody() {
    return body;
  }

  public RequestDto setBody(String body) {
    this.body = body;
    return this;
  }
}
