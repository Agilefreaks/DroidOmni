package com.omnipasteapp.omniapi.resources;

import com.google.gson.Gson;
import com.omnipasteapp.omniapi.AsyncRequestTask;
import com.omnipasteapp.omniapi.handlers.GetResponseHandler;
import com.omnipasteapp.omniapi.handlers.PostResponseHandler;
import com.omnipasteapp.omnicommon.models.Clipping;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

public class Clippings extends Resource implements IClippings {
  public static final String Url = "clippings";

  @Override
  public void saveAsync(String data, final ISaveClippingCompleteHandler handler) {
    Gson gson = new Gson();
    HttpPost postRequest = new HttpPost(getUri());
    postRequest.setHeader("Content-Type", "application/json");

    String json = gson.toJson(new Clipping(getApiKey(), data));

    try {
      StringEntity se = new StringEntity(json, HTTP.UTF_8);
      postRequest.setEntity(se);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    new AsyncRequestTask(postRequest, new PostResponseHandler(handler)).execute();
  }

  @Override
  public void getLastAsync(final IFetchClippingCompleteHandler handler) {
    HttpGet getRequest = new HttpGet(getUri());
    getRequest.addHeader("Accept", "application/json");
    getRequest.addHeader("Channel", getApiKey());

    new AsyncRequestTask(getRequest, new GetResponseHandler(handler)).execute();
  }

  @Override
  protected String getUrl() {
    return Url;
  }
}
