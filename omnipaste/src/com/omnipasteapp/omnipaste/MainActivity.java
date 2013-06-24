package com.omnipasteapp.omnipaste;

import android.os.Bundle;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
  @Inject
  private IOmniService _omniService;

  @Inject
  private IConfigurationService _configurationService;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    _configurationService.loadCommunicationSettings();

    if (_omniService.isConfigured()) {
      _intentService.sendBroadcast(BackgroundServiceCommandReceiver.class, BackgroundServiceCommandReceiver.START_SERVICE);
    } else {
      _intentService.startClearActivity(LoginActivity.class);
    }
  }
}
