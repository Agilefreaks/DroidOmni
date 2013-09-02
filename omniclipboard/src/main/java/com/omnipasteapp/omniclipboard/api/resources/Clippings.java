package com.omnipasteapp.omniclipboard.api.resources;

import android.net.http.AndroidHttpClient;

import com.google.gson.Gson;
import com.omnipasteapp.omniclipboard.api.AsyncRequestTask;
import com.omnipasteapp.omniclipboard.api.IGetClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.ISaveClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.models.Clipping;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Clippings implements IClippings {
  public static final String Url = "clippings";

  private final String BaseUrl;
  private final String Version;
  private final String ApiKey;

  private final AndroidHttpClient httpClient = AndroidHttpClient.newInstance("DroidOmni");
  private final Gson gson = new Gson();

  public Clippings(String baseUrl, String version, String apiKey) {
    BaseUrl = baseUrl;
    Version = version;
    ApiKey = apiKey;
  }

  @Override
  public void saveAsync(String data, final ISaveClippingCompleteHandler handler) {
    HttpPost postRequest = new HttpPost(getUri());
    postRequest.setHeader("Content-Type", "application/json");

    String json = gson.toJson(new Clipping(ApiKey, data));

    try {
      StringEntity se = new StringEntity(json, HTTP.UTF_8);
      postRequest.setEntity(se);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    new AsyncRequestTask<Object>(postRequest, httpClient, new ResponseHandler<Object>() {
      @Override
      public Object handleResponse(HttpResponse httpResponse) {
        if (httpResponse.getStatusLine().getStatusCode() == 201) {
          handler.saveClippingSucceeded();
        } else {
          handler.saveClippingFailed(httpResponse.getStatusLine().getReasonPhrase());
        }

        return null;
      }
    }).execute();
  }

  @Override
  public void getLastAsync(final IGetClippingCompleteHandler handler) {
    HttpGet getRequest = new HttpGet(getUri());
    getRequest.addHeader("Accept", "application/json");
    getRequest.addHeader("Channel", ApiKey);

    new AsyncRequestTask<Object>(getRequest, httpClient, new ResponseHandler<Object>() {
      @Override
      public Object handleResponse(HttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
          Clipping clipping = gson.fromJson(new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())), Clipping.class);
          handler.handleClipping(clipping.getContent());
        }

        return null;
      }
    }).execute();
  }

  private String getUri() {
    return BaseUrl + "/" + Version + "/" + Url;
  }
}
