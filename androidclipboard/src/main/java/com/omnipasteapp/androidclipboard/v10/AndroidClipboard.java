package com.omnipasteapp.androidclipboard.v10;

import android.annotation.TargetApi;
import android.os.Build;

import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.models.Clipping;

import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class AndroidClipboard implements ILocalClipboard, Runnable, IClipboardManagerWrapper.OnClipChangedListener {

  public IClipboardManagerWrapper _clipboardManager;

  private ArrayList<ICanReceiveData> dataReceivers;

  public AndroidClipboard(IClipboardManagerWrapper clipboardManager) {
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
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public void dispose() {
    dataReceivers.clear();
    _clipboardManager.dispose();
  }

  @Override
  public void putData(String data) {
    _clipboardManager.setClip(data);
  }

  @Override
  public void run() {
    _clipboardManager.setOnClipChangedListener(this);
    _clipboardManager.start();
  }

  @Override
  public void onPrimaryClipChanged() {
    if (!hasClipping()) {
      return;
    }

    Clipping clip = new Clipping("", _clipboardManager.getCurrentClip());

    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(new ClipboardData(this, clip));
    }
  }

  private boolean hasClipping() {
    return _clipboardManager.hasClipping();
  }
}
