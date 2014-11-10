package com.omnipaste.droidomni;

import android.content.Context;

public final class Modules {
  static Object[] list(Context context) {
    return new Object[] {
      new RootModule(context)
    };
  }

  private Modules() {
  }
}
