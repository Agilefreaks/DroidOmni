package com.omnipasteapp.omnipaste;

import android.os.Bundle;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {
  @Inject
  IOmniService omniService;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
      getOmniService().start();
    } catch (InterruptedException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }

  public IOmniService getOmniService() {
    return omniService;
  }

  public void setOmniService(IOmniService omniService) {
    this.omniService = omniService;
  }
}
