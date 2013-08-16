package com.omnipasteapp.omniclipboard.api.resources;

import com.omnipasteapp.omniclipboard.api.IGetClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.ISaveClippingCompleteHandler;

public interface IClippings {
  void saveAsync(String data, ISaveClippingCompleteHandler handler);

  void getLastAsync(IGetClippingCompleteHandler handler);
}
