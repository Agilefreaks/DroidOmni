package com.omnipasteapp.omnipaste.activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.receivers.ConnectivityReceiver_;
import com.omnipasteapp.omnipaste.receivers.OmnipasteStatusChangedReceiver;
import com.omnipasteapp.omnipaste.services.IIntentService;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends SherlockFragmentActivity implements LogoutDialog.LogoutDialogListener, IOmnipasteStatusChangedDisplay {
  private OmnipasteStatusChangedReceiver _statusChangedReceiver;
  private ConnectivityReceiver_ _connectivityReceiver;

  //region Public properties
  @Inject
  public IConfigurationService configurationService;

  @Inject
  public IIntentService intentService;

  @StringRes
  public String startOmnipasteService;

  @StringRes
  public String stopOmnipasteService;

  @StringRes
  public String omnipasteServiceStatusChanged;

  @StringRes
  public String appName;

  @StringRes
  public String textServiceConnected;

  @StringRes
  public String textServiceDisconnected;
  //endregion

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);
  }

  @Override
  public void onDestroy() {
    unregisterReceivers();

    super.onDestroy();
  }

  @AfterViews
  public void startOmnipasteService() {
    if (configurationService.loadCommunicationSettings()) {
      registerReceivers();

      getSupportActionBar().setTitle(R.string.app_name);
      getSupportActionBar().setSubtitle(configurationService.getCommunicationSettings().getChannel());
    } else {
      intentService.startNewActivity(LoginActivity_.class);
    }
  }

  //region IOmnipasteStatusChangedDisplay
  @Override
  public void omnipasteServiceStarted() {
    getSupportActionBar().setTitle(appName + " (" + textServiceConnected +")");
  }

  @Override
  public void omnipasteServiceStopped() {
    getSupportActionBar().setTitle(appName + " (" + textServiceDisconnected +")");
  }
  //endregion

  //region logout
  @OptionsItem
  public void logoutSelected() {
    LogoutDialog.create().show(getSupportFragmentManager(), LogoutDialog.TAG);
  }

  @Override
  public void onDialogPositiveClick(DialogFragment dialog) {
    // kill service
    intentService.sendBroadcast(stopOmnipasteService);

    // log user out
    configurationService.clearChannel();

    // go to login activity
    finish();
    intentService.startNewActivity(LoginActivity_.class);
  }

  @Override
  public void onDialogNegativeClick(DialogFragment dialog) {
  }
  //endregion

  //region private methods
  private void registerReceivers() {
    _connectivityReceiver = new ConnectivityReceiver_();
    IntentFilter connectivityIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    registerReceiver(_connectivityReceiver, connectivityIntentFilter);

    _statusChangedReceiver = new OmnipasteStatusChangedReceiver(this);
    IntentFilter intentFilter = new IntentFilter(omnipasteServiceStatusChanged);
    registerReceiver(_statusChangedReceiver, intentFilter);
  }

  private void unregisterReceivers() {
    unregisterReceiver(_statusChangedReceiver);
    unregisterReceiver(_connectivityReceiver);
  }
  //endregion
}
