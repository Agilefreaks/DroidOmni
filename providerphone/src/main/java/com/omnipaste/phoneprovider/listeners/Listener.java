package com.omnipaste.phoneprovider.listeners;

import android.content.Context;

public interface Listener {
  public void start(Context context, String deviceId);

  public void stop();
}
