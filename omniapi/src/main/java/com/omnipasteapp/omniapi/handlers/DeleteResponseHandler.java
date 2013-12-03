package com.omnipasteapp.omniapi.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public class DeleteResponseHandler implements ResponseHandler {
  private IDeleteResourceCompleteHandler _deleteResourceCompleteHandler;

  public DeleteResponseHandler(IDeleteResourceCompleteHandler deleteResourceCompleteHandler) {
    _deleteResourceCompleteHandler = deleteResourceCompleteHandler;
  }

  @Override
  public Object handleResponse(HttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusLine().getStatusCode() == 201) {
      _deleteResourceCompleteHandler.deleteSuccess();
    } else {
      _deleteResourceCompleteHandler.callFailed(httpResponse.getStatusLine().getReasonPhrase());
    }

    return null;
  }
}
