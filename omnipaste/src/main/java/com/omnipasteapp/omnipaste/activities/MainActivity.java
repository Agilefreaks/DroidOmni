package com.omnipasteapp.omnipaste.activities;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.services.IIntentService;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class MainActivity extends SherlockActivity {

  @Inject
  public IConfigurationService configurationService;

  @Inject
  public IIntentService intentService;

  @StringRes
  public String startOmnipasteService;

  @StringRes
  public String stopOmnipasteService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);
  }

  @AfterViews
  public void startOmnipasteService() {
    if(configurationService.loadCommunicationSettings()) {
      intentService.sendBroadcast(startOmnipasteService);

      getSupportActionBar().setTitle(R.string.app_name);
      getSupportActionBar().setSubtitle(configurationService.getCommunicationSettings().getChannel());
    }
    else {
      intentService.startNewActivity(LoginActivity_.class);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getSupportMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
}
