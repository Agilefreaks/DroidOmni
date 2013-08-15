package com.omnipasteapp.omnipaste.providers;

import android.content.Context;

import com.omnipasteapp.omnicommon.interfaces.IAppConfigurationProvider;

import javax.inject.Inject;

public class AppConfigurationProvider implements IAppConfigurationProvider{

  @Inject
  public Context context;

  @Override
  public String getValue(String key) {
    int resId = context.getResources().getIdentifier(key,
        "string", context.getPackageName());

    String str = context.getString(resId);

    return str;
  }

  @Override
  public boolean setValue(String key, String value) {
    return false;
  }
}
