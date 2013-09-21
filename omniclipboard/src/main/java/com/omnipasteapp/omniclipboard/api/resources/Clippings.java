package com.omnipasteapp.omniclipboard.api.resources;

import com.google.gson.Gson;
import com.omnipasteapp.omniclipboard.api.AsyncRequestTask;
import com.omnipasteapp.omniclipboard.api.IGetClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.ISaveClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.handlers.GetResponseHandler;
import com.omnipasteapp.omniclipboard.api.handlers.PostResponseHandler;
import com.omnipasteapp.omniclipboard.api.models.Clipping;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

public class Clippings implements IClippings {
  public static final String Url = "clippings";

  private final String BaseUrl;
  private final String Version;
  private final String ApiKey;

  public Clippings(String baseUrl, String version, String apiKey) {
    BaseUrl = baseUrl;
    Version = version;
    ApiKey = apiKey;
  }

  @Override
  public void saveAsync(String data, final ISaveClippingCompleteHandler handler) {
    Gson gson = new Gson();
    HttpPost postRequest = new HttpPost(getUri());
    postRequest.setHeader("Content-Type", "application/json");

    String json = gson.toJson(new Clipping(ApiKey, data));

    try {
      StringEntity se = new StringEntity(json, HTTP.UTF_8);
      postRequest.setEntity(se);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    new AsyncRequestTask(postRequest, new PostResponseHandler(handler)).execute();
  }

  @Override
  public void getLastAsync(final IGetClippingCompleteHandler handler) {
    HttpGet getRequest = new HttpGet(getUri());
    getRequest.addHeader("Accept", "application/json");
    getRequest.addHeader("Channel", ApiKey);

    new AsyncRequestTask(getRequest, new GetResponseHandler(handler)).execute();
  }

  private String getUri() {
    return BaseUrl + "/" + Version + "/" + Url;
  }
}
