package com.omnipasteapp.omniclipboard.api.handlers;

import com.google.gson.Gson;
import com.omnipasteapp.omniclipboard.api.IGetClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.models.Clipping;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetResponseHandler implements ResponseHandler {
  private IGetClippingCompleteHandler getClippingCompleteHandler;

  public GetResponseHandler(IGetClippingCompleteHandler getClippingCompleteHandler) {
    this.getClippingCompleteHandler = getClippingCompleteHandler;
  }

  @Override
  public Object handleResponse(HttpResponse httpResponse) throws IOException {
    Gson gson = new Gson();

    if (httpResponse.getStatusLine().getStatusCode() == 200) {
      Clipping clipping = gson.fromJson(new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())), Clipping.class);
      getClippingCompleteHandler.handleClipping(clipping.getContent());
    }

    return null;
  }
}
