package com.omnipaste.droidomni;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class DebugGoogleCloudMessaging extends GoogleCloudMessaging {
  @Override public String register(String... senderIds) throws IOException {
    return "debug google token id";
  }
}
