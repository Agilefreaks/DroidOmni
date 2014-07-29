package com.omnipaste.clipboardprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.ClippingDto;

public interface IClipboardProvider extends Provider<ClippingDto> {
  void refreshLocal();

  void refreshOmni();
}
