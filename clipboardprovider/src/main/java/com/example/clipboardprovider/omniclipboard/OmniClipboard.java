package com.example.clipboardprovider.omniclipboard;

import android.util.Log;

import com.omnipasteapp.omniapi.OmniApi;
import com.omnipasteapp.omniapi.resources.clippings.IFetchClippingCompleteHandler;
import com.omnipasteapp.omniapi.resources.clippings.ISaveClippingCompleteHandler;
import com.omnipasteapp.omnicommon.interfaces.ConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.models.Clipping;

import java.util.ArrayList;

import javax.inject.Inject;

public class OmniClipboard implements IOmniClipboard, Runnable, IFetchClippingCompleteHandler, ISaveClippingCompleteHandler {
  private final OmniApi omniApi;
  private final ConfigurationService configurationService;
  private ArrayList<ICanReceiveData> dataReceivers;

  @Inject
  public OmniClipboard(OmniApi omniApi) {
    this.omniApi = omniApi;
    this.configurationService = configurationService;

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
    dataReceivers.clear();
  }

  @Override
  public void putData(String data) {
    omniApi.clippings().saveAsync(data, getRegistrationId(), this);
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
  }

  private String getRegistrationId() {
    return configurationService.getCommunicationSettings().getRegistrationId();
  }
}
