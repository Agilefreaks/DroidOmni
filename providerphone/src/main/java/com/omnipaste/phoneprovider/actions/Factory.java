package com.omnipaste.phoneprovider.actions;

import com.omnipaste.omnicommon.domain.PhoneAction;

public interface Factory {
  public Action create(PhoneAction phoneAction);
}
