package com.omnipasteapp.omnipaste.activities;

import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.providers.SharedPreferencesConfigurationProvider;

public class LaunchActivityTest extends ActivityInstrumentationTestCase2<LaunchActivity_> {
  public LaunchActivityTest() {
    super(LaunchActivity_.class);
  }

  public void testOnCreateWhenConfigurationServiceHasNoChannelItLaunchesLoginActivity() throws Throwable {
    Instrumentation instrumentation = getInstrumentation();
    Instrumentation.ActivityMonitor loginActivityMonitor = new Instrumentation.ActivityMonitor(LoginActivity_.class.getName(), null, false);
    instrumentation.addMonitor(loginActivityMonitor);

    runTestOnUiThread(new Thread() {
      @Override
      public void run() {
        cleanSharedPreferences();
      }
    });

    getActivity();

    assertTrue(instrumentation.checkMonitorHit(loginActivityMonitor, 1));
  }

  public void testOnCreateWhenConfigurationServiceHasChannelItLaunchesMainActivity() throws Throwable {
    Instrumentation instrumentation = getInstrumentation();
    Instrumentation.ActivityMonitor loginActivityMonitor = new Instrumentation.ActivityMonitor(MainActivity_.class.getName(), null, false);
    instrumentation.addMonitor(loginActivityMonitor);

    runTestOnUiThread(new Thread() {
      @Override
      public void run() {
        setSharedPreferences();
      }

    });

    getActivity();

    assertTrue(instrumentation.checkMonitorHit(loginActivityMonitor, 1));
  }

  private void cleanSharedPreferences() {
    setChannel("");
  }

  private void setSharedPreferences() {
    setChannel("testChannel");
  }

  @SuppressWarnings("ConstantConditions")
  private void setChannel(String value) {
    Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
    SharedPreferences preferences = context.getSharedPreferences(SharedPreferencesConfigurationProvider.SharedPreferenceKey, Context.MODE_PRIVATE);

    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(CommunicationSettings.ChannelKey, value);
    editor.commit();
  }
}
