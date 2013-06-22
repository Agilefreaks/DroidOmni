package com.omnipasteapp.omnicommon.framework;

import android.content.Context;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import roboguice.RoboGuice;

public class OmniServiceFactory implements IOmniServiceFactory{

  @Inject
  private Context context;

  @Override
  public IOmniService create() {
    return RoboGuice.getInjector(context).getInstance(IOmniService.class);
  }
}
