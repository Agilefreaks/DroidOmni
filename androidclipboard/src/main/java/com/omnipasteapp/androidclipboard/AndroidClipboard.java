package com.omnipasteapp.androidclipboard;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;

import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidClipboard implements ILocalClipboard, Runnable, ClipboardManager.OnPrimaryClipChangedListener {

  private ClipboardManager _clipboardManager;

  private ArrayList<ICanReceiveData> dataReceivers;

  public AndroidClipboard(ClipboardManager clipboardManager) {
    dataReceivers = new ArrayList<ICanReceiveData>();
    _clipboardManager = clipboardManager;
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
    _clipboardManager.setPrimaryClip(ClipData.newPlainText("", data));
  }

  @Override
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public void run() {
    _clipboardManager.addPrimaryClipChangedListener(this);
  }

  @Override
  public void dispose() {
    _clipboardManager.removePrimaryClipChangedListener(this);

    dataReceivers.clear();
  }

  @Override
  public void onPrimaryClipChanged() {
    if (!hasClipping()) {
      return;
    }

    @SuppressWarnings("ConstantConditions") String clip = getPrimaryClip().getItemAt(0).getText().toString();

    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(new ClipboardData(this, clip));
    }
  }

  private Boolean hasClipping() {
    return _clipboardManager.hasPrimaryClip() && getPrimaryClip().getItemCount() > 0;
  }

  private ClipData getPrimaryClip() {
    ClipData result = _clipboardManager.getPrimaryClip();

    if (result == null) {
      result = new ClipData("", new String[] {""}, new ClipData.Item(""));
    }

    return result ;
  }
}