package com.omnipasteapp.omnipaste.activities;

import com.omnipasteapp.omnipaste.enums.Sender;

public interface IOmnipasteDataDisplay {
  void omnipasteDataReceived(String data, Sender sender);
}
