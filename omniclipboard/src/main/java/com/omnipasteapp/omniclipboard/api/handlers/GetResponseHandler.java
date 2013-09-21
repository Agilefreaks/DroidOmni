package com.omnipasteapp.omniclipboard.api.handlers;

import com.omnipasteapp.omniclipboard.api.IGetClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.builders.ClippingBuilder;
import com.omnipasteapp.omniclipboard.api.models.Clipping;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetResponseHandler implements ResponseHandler<Void> {
  private IGetClippingCompleteHandler getClippingCompleteHandler;

  public GetResponseHandler(IGetClippingCompleteHandler getClippingCompleteHandler) {
    this.getClippingCompleteHandler = getClippingCompleteHandler;
  }

  @Override
  public Void handleResponse(HttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusLine().getStatusCode() == 200) {
      ClippingBuilder clippingBuilder = new ClippingBuilder();
      Clipping clipping = clippingBuilder.build(new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())));

      getClippingCompleteHandler.handleClipping(clipping);
    }

    return null;
  }
}
