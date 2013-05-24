package com.omnipasteapp.androidclipboard;

import android.content.Context;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

public class AndroidClipboard implements ILocalClipboard {
  @Inject
  Context context;

  @Override
  public void addDataReceiver(ICanReceiveData dataReceiver) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void removeDataReceive(ICanReceiveData dataReceiver) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Thread initialize() {
    return new Thread();
  }

  @Override
  public void dispose() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void putData(String data) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
