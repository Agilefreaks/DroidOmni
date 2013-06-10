package com.omnipasteapp.omnipaste;

import android.os.Bundle;
import android.util.Log;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {
  @Inject
  private IOmniService omniService;

  @Inject
  private IConfigurationService configurationService;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    configurationService.loadCommunicationSettings();

    try {
      omniService.start();
    } catch (InterruptedException e) {
      e.printStackTrace(); // handle this in a smarter way
    }
  }
}
