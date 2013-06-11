package com.omnipasteapp.omnipaste;

import android.content.Intent;
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

    if(omniService.isConfigured()){
      startService();
    } else {
      startConfiguring();
    }
  }

  public void startService(){
    try {
      omniService.start();
    } catch (InterruptedException e) {
      e.printStackTrace(); // handle this in a smarter way
    }
  }

  public void startConfiguring(){
    startActivity(new Intent(this, LoginActivity.class));
  }
}
