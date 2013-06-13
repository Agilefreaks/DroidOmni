package com.omnipasteapp.omnipaste;

import android.os.Bundle;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
  @Inject
  private IOmniService omniService;

  @Inject
  private IConfigurationService configurationService;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    configurationService.loadCommunicationSettings();

    if (omniService.isConfigured()) {
      intentService.startService(BackgroundService.class);
    } else {
      intentService.startClearActivity(LoginActivity.class);
    }
  }
}
