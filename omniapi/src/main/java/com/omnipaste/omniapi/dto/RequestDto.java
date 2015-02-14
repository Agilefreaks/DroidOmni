package com.omnipaste.omniapi.dto;

public class RequestDto<T> {
  private String method, path;
  private T body;

  public String getMethod() {
    return method;
  }

  public RequestDto<T> setMethod(String method) {
    this.method = method;
    return this;
  }

  public String getPath() {
    return path;
  }

  public RequestDto<T> setPath(String path) {
    this.path = path;
    return this;
  }

  public T getBody() {
    return body;
  }

  public RequestDto<T> setBody(T body) {
    this.body = body;
    return this;
  }
}
