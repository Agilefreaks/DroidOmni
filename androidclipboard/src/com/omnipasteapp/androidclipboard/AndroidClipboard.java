package com.omnipasteapp.androidclipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Looper;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import roboguice.RoboGuice;

import java.util.ArrayList;

public class AndroidClipboard implements ILocalClipboard, Runnable, ClipboardManager.OnPrimaryClipChangedListener {

  @Inject
  private Context context;

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
    ClipboardManager manager = clipboardManager;

    manager.setPrimaryClip(ClipData.newPlainText("", data));
  }

  @Override
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public void run() {
    clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    clipboardManager.addPrimaryClipChangedListener(this);
  }

  @Override
  public void dispose() {
    ClipboardManager manager = clipboardManager;
    manager.removePrimaryClipChangedListener(this);

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
