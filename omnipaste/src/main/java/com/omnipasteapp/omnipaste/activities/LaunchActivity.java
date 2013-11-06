package com.omnipasteapp.omnipaste.activities;


import android.app.Activity;
import android.os.Bundle;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.services.IIntentHelper;

import javax.inject.Inject;

@NoTitle
@EActivity
public class LaunchActivity extends Activity {

  //region Public Properties

  @Inject
  public IConfigurationService configurationService;

  @Inject
  public IIntentHelper intentService;

  //endregion

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);

    configurationService.initialize();
    if (configurationService.getCommunicationSettings().hasChannel()) {
      finish();
      intentService.startNewActivity(MainActivity_.class);
    }
    else {
      finish();
      intentService.startNewActivity(LoginActivity_.class);
    }
  }
}
