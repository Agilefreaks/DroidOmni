package com.omnipasteapp.omniclipboard.api;

import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

public class AsyncRequestTask<T> extends AsyncTask {

  private final HttpUriRequest request;
  private final HttpClient client;
  private final ResponseHandler<T> handler;

  public AsyncRequestTask(HttpUriRequest request, HttpClient client, ResponseHandler<T> handler) {
    this.request = request;
    this.client = client;
    this.handler = handler;
  }

  @Override
  protected Object doInBackground(Object[] params) {
    boolean executed;
    try {
      client.execute(request, handler);
      executed = true;
    } catch (Throwable e) {
      e.printStackTrace();
      executed = false;
    }

    return executed;
  }
}
