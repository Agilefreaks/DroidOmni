package com.omnipasteapp.omniapi.handlers;

public interface IFetchResourceCompleteHandler<T> extends IFailHandler {
  void fetchSuccess(T resource);
}
