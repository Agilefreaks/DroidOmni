package com.omnipasteapp.omniapi.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public class PostResponseHandler implements ResponseHandler {
  private ISaveResourceCompleteHandler _saveResourceCompleteHandler;

  public PostResponseHandler(ISaveResourceCompleteHandler saveResourceCompleteHandler) {
    _saveResourceCompleteHandler = saveResourceCompleteHandler;
  }

  @Override
  public Object handleResponse(HttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusLine().getStatusCode() == 201) {
      _saveResourceCompleteHandler.saveSuccess();
    } else {
      _saveResourceCompleteHandler.callFailed(httpResponse.getStatusLine().getReasonPhrase());
    }

    return null;
  }
}
