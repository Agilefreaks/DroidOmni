package com.omnipaste.phoneprovider.actions;

import com.omnipaste.phoneprovider.PhoneAction;

public interface Factory {
  public Action create(PhoneAction phoneAction);
}
