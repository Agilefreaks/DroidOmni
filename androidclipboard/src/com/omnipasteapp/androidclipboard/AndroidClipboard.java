package com.omnipasteapp.androidclipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

import java.util.ArrayList;

public class AndroidClipboard implements ILocalClipboard, Runnable, ClipboardManager.OnPrimaryClipChangedListener {

  @Inject
  private ClipboardManager clipboardManager;

  private ArrayList<ICanReceiveData> dataReceivers;

  public AndroidClipboard() {
    dataReceivers = new ArrayList<ICanReceiveData>();
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
  public void putData(String data) {
    clipboardManager.setPrimaryClip(ClipData.newPlainText("", data));
  }

  @Override
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public void run() {
    clipboardManager.addPrimaryClipChangedListener(this);
  }

  @Override
  public void dispose() {
    clipboardManager.removePrimaryClipChangedListener(this);

    dataReceivers.clear();
  }

  @Override
  public void onPrimaryClipChanged() {
    if (!hasClipping()) {
      return;
    }

    String clip = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();

    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(new ClipboardData(this, clip));
    }
  }

  private Boolean hasClipping() {
    return clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0;
  }
}
