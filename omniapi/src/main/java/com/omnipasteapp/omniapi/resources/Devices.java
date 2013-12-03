package com.omnipasteapp.omniapi.resources;

import com.google.gson.Gson;
import com.omnipasteapp.omniapi.AsyncRequestTask;
import com.omnipasteapp.omniapi.handlers.DeleteResponseHandler;
import com.omnipasteapp.omniapi.handlers.PostResponseHandler;
import com.omnipasteapp.omnicommon.models.Device;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

public class Devices extends Resource implements IDevices {
  public static final String Url = "devices";

  @Override
  public void saveAsync(String registrationId, ISaveDeviceCompleteHandler handler) {
    Gson gson = new Gson();
    HttpPost postRequest = new HttpPost(getUri());
    postRequest.setHeader("Content-Type", "application/json");
    postRequest.setHeader("Channel", getApiKey());

    String json = gson.toJson(new Device(registrationId));

    try {
      StringEntity se = new StringEntity(json, HTTP.UTF_8);
      postRequest.setEntity(se);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    new AsyncRequestTask(postRequest, new PostResponseHandler(handler)).execute();
  }

  @Override
  public void deleteAsync(String registrationId, IDeleteDeviceCompleteHandler handler) {
    HttpDelete deleteRequest = new HttpDelete(getUri() + "/" + registrationId);
    deleteRequest.setHeader("Channel", getApiKey());

    new AsyncRequestTask(deleteRequest, new DeleteResponseHandler(handler)).execute();
  }

  @Override
  protected String getUrl() {
    return Url;
  }
}
