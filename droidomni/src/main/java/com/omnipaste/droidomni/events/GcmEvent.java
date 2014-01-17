package com.omnipaste.droidomni.events;

import android.os.Bundle;

public class GcmEvent {
  public Bundle extras;

  public GcmEvent(Bundle extras) {
    this.extras = extras;
  }
}
