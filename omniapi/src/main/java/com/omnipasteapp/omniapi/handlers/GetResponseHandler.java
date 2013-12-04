package com.omnipasteapp.omniapi.handlers;

import com.omnipasteapp.omniapi.builders.ClippingBuilder;
import com.omnipasteapp.omnicommon.models.Clipping;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetResponseHandler implements ResponseHandler<Void> {
  private IFetchResourceCompleteHandler<Clipping> _fetchResourceCompleteHandler;

  public GetResponseHandler(IFetchResourceCompleteHandler<Clipping> fetchResourceCompleteHandler) {
    _fetchResourceCompleteHandler = fetchResourceCompleteHandler;
  }

  @Override
  public Void handleResponse(HttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusLine().getStatusCode() == 200) {
      // TODO: generify this to work with other than Clipping
      ClippingBuilder clippingBuilder = new ClippingBuilder();
      Clipping clipping = clippingBuilder.build(new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())));

      _fetchResourceCompleteHandler.fetchSuccess(clipping);
    }

    return null;
  }
}
