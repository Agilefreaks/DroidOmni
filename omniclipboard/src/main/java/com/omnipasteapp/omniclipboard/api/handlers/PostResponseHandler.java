package com.omnipasteapp.omniclipboard.api.handlers;

import com.omnipasteapp.omniclipboard.api.ISaveClippingCompleteHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public class PostResponseHandler implements ResponseHandler {
  private ISaveClippingCompleteHandler saveClippingCompleteHandler;

  public PostResponseHandler(ISaveClippingCompleteHandler saveClippingCompleteHandler) {
    this.saveClippingCompleteHandler = saveClippingCompleteHandler;
  }

  @Override
  public Object handleResponse(HttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusLine().getStatusCode() == 201) {
      saveClippingCompleteHandler.saveClippingSucceeded();
    } else {
      saveClippingCompleteHandler.saveClippingFailed(httpResponse.getStatusLine().getReasonPhrase());
    }

    return null;
  }
}
