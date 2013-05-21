package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;

public class OmniService implements IOmniService {
  private ILocalClipboard localClipboard;
  private IOmniClipboard omniClipboard;

  public OmniService(ILocalClipboard localClipboard, IOmniClipboard omniClipboard) {
    this.localClipboard = localClipboard;
    this. omniClipboard = omniClipboard;
  }

  @Override
  public ILocalClipboard getLocalClipboard() {
    return localClipboard;
  }

  @Override
  public IOmniClipboard getOmniClipboard() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void start() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void stop() {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
