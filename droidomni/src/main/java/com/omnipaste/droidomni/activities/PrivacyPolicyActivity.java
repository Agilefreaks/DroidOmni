package com.omnipaste.droidomni.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.omnipaste.droidomni.R;

public class PrivacyPolicyActivity extends ActionBarActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindow().requestFeature(Window.FEATURE_PROGRESS);

    WebView webView = new WebView(this);
    setContentView(webView);

    getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

    final PrivacyPolicyActivity privacyPolicyActivity = this;
    webView.setWebChromeClient(new WebChromeClient() {
      public void onProgressChanged(WebView view, int progress) {
        // Activities and WebViews measure progress with different scales.
        // The progress meter will automatically disappear when we reach 100%
        privacyPolicyActivity.setProgress(progress * 1000);
      }
    });

    webView.loadUrl(getResources().getString(R.string.tos_url));
  }
}
