package com.omnipaste.droidomni;

import android.content.Context;

import com.omnipaste.droidomni.di.DebugRootModule;
import com.omnipaste.droidomni.di.RootModule;

import java.util.ArrayList;

public final class Modules {
  static Object[] list(Context context) {
    ArrayList<Object> modules = new ArrayList<>();
    modules.add(new RootModule(context));

    if (BuildConfig.DEBUG) {
      modules.add(new DebugRootModule());
    }

    return modules.toArray();
  }

  private Modules() {

  }
}
