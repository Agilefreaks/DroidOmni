package com.omnipasteapp.omnipaste.activities;

import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;

import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnipaste.backgroundServices.OmnipasteService_;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity_> {
  public MainActivityTest() {
    super(MainActivity_.class);
  }

  public void testClippingsArePersistedBetweenLaunches() throws Throwable {
    // don't launch login mainActivity
    final Instrumentation instrumentation = getInstrumentation();
    instrumentation.addMonitor(LoginActivity_.class.getName(), null, true);

    final MainActivity_ mainActivity = getActivity();

    // send a content message
    Bundle bundle = new Bundle();
    bundle.putParcelable(OmnipasteService_.EXTRA_CLIPPING, new Clipping("token", "content"));
    Message message = Message.obtain(null, OmnipasteService_.MSG_DATA_RECEIVED);
    if (message != null) {
      message.setData(bundle);
    }
    mainActivity.messenger.send(message);

    // callActivityOnSaveInstanceState
    final Bundle outState = new Bundle();
    runTestOnUiThread(new Thread() {
      @Override
      public void run() {
        instrumentation.callActivityOnSaveInstanceState(mainActivity, outState);
      }
    });

    // callActivityOnRestoreInstanceState
    runTestOnUiThread(new Thread() {
      @Override
      public void run() {
        instrumentation.callActivityOnRestoreInstanceState(mainActivity, outState);
      }
    });
  }
}