package com.omnipasteapp.omniclipboard.api;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

public class AsyncRequestTask extends AsyncTask<Object, Void, Boolean> {

  private final HttpUriRequest request;
  private final HttpClient client;
  private final ResponseHandler handler;

  public AsyncRequestTask(HttpUriRequest request, ResponseHandler handler) {
    this.request = request;
    this.handler = handler;

    this.client = AndroidHttpClient.newInstance("DroidOmni");
  }

  @Override
  protected Boolean doInBackground(Object[] params) {
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
