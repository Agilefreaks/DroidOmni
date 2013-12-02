package com.omnipasteapp.omniapi;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

public class AsyncRequestTask extends AsyncTask<Object, Void, Boolean> {
  private final HttpUriRequest request;
  private final ResponseHandler handler;

  public AsyncRequestTask(HttpUriRequest request, ResponseHandler handler) {
    this.request = request;
    this.handler = handler;
  }

  @Override
  protected Boolean doInBackground(Object[] params) {
    HttpResponse response = null;
    AndroidHttpClient client = AndroidHttpClient.newInstance("DroidOmni");

    try {
      response = client.execute(request);
      handler.handleResponse(response);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    finally {
      client.close();
    }

    return response != null;
  }
}
