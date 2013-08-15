package com.omnipasteapp.api;

import android.net.http.AndroidHttpClient;

import com.google.gson.Gson;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.ApiConfig;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

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

import javax.inject.Inject;

public class OmniApi implements IOmniApi {
  private final AndroidHttpClient httpClient;
  private final Gson gson;
  private ApiConfig apiConfig;
  private CommunicationSettings communicationSettings;

  @Inject
  public OmniApi(IConfigurationService configurationService) {
    apiConfig = configurationService.getApiConfig();
    communicationSettings = configurationService.getCommunicationSettings();

    gson = new Gson();

    httpClient = AndroidHttpClient.newInstance("DroidOmni");
  }

  @Override
  public void saveClippingAsync(String data, final ISaveClippingCompleteHandler handler) {
    String json = gson.toJson(new Clipping(communicationSettings.getChannel(), data));
    HttpPost postRequest = new HttpPost(apiConfig.getBaseUrl() + "/" + apiConfig.getResources().getClippings());

    try {
      StringEntity se = new StringEntity(json, HTTP.UTF_8);
      postRequest.setEntity(se);
      postRequest.setHeader("Content-Type", "application/json");
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
  public void getLastClippingAsync(final IGetClippingCompleteHandler handler) {
    HttpGet getRequest = new HttpGet(apiConfig.getBaseUrl() + "/" + apiConfig.getResources().getClippings());
    getRequest.addHeader("Accept", "application/json");
    getRequest.addHeader("Channel", communicationSettings.getChannel());

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
}
