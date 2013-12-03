package com.omnipasteapp.clipboardprovider.omniclipboard;

import android.util.Log;

import com.omnipasteapp.omniapi.IOmniApi;
import com.omnipasteapp.omniapi.resources.IFetchClippingCompleteHandler;
import com.omnipasteapp.omniapi.resources.ISaveClippingCompleteHandler;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.models.Clipping;

import java.util.ArrayList;

import javax.inject.Inject;

public class OmniClipboard implements IOmniClipboard, Runnable, IFetchClippingCompleteHandler, ISaveClippingCompleteHandler {
  private final IOmniApi omniApi;
  private ArrayList<ICanReceiveData> dataReceivers;

  @Inject
  public OmniClipboard(IOmniApi omniApi) {
    this.omniApi = omniApi;

    dataReceivers = new ArrayList<>();
  }

  // region IOmniClipboard/IClipboard

  @Override
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public void addDataReceiver(ICanReceiveData dataReceiver) {
    dataReceivers.add(dataReceiver);
  }

  @Override
  public void removeDataReceiver(ICanReceiveData dataReceiver) {
    dataReceivers.remove(dataReceiver);
  }

  @Override
  public void dispose() {
  }

  @Override
  public void putData(String data) {
    omniApi.clippings().saveAsync(data, this);
  }

  // endregion

  // region FetchCompleteHandler

  @Override
  public void fetchSuccess(Clipping resource) {
    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(resource);
    }
  }

  @Override
  public void callFailed(String reason) {
    Log.i("OmniClipboard", reason);
  }

  // endregion

  // region SaveClippingCompleteHandler

  @Override
  public void saveSuccess() {
    Log.i("OmniClipboard", "Clipping saved");
  }

  // endregion

  @Override
  public void fetchClipping() {
    omniApi.clippings().getLastAsync(this);
  }

  @Override
  public synchronized void run() {
//    communicationSettings = configurationService.getCommunicationSettings();
//    messagingService.connect(getChannel(), this);

//    OmniApi.setApiKey(getChannel());
  }
}
