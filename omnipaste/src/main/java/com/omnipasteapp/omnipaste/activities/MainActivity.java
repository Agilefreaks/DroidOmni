package com.omnipasteapp.omnipaste.activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.enums.Sender;
import com.omnipasteapp.omnipaste.receivers.ConnectivityReceiver_;
import com.omnipasteapp.omnipaste.receivers.OmnipasteDataReceiver;
import com.omnipasteapp.omnipaste.receivers.OmnipasteStatusChangedReceiver;
import com.omnipasteapp.omnipaste.services.IIntentService;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends SherlockFragmentActivity implements LogoutDialog.LogoutDialogListener, IOmnipasteStatusChangedDisplay, IOmnipasteDataDisplay {
  private OmnipasteStatusChangedReceiver _statusChangedReceiver;
  private ConnectivityReceiver_ _connectivityReceiver;
  private OmnipasteDataReceiver _omnipasteDataReceiver;
  private ArrayAdapter<String> _dataListAdapter;

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
  public String omnipasteDataReceived;

  @StringRes
  public String appName;

  @StringRes
  public String textServiceConnected;

  @StringRes
  public String textServiceDisconnected;

  @ViewById
  public ListView dataListView;
  //endregion

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);
  }

  @Override
  public void onDestroy() {
    unregisterReceivers();
    intentService.sendBroadcast(stopOmnipasteService);

    super.onDestroy();
  }

  @AfterViews
  public void startOmnipasteService() {
    if (configurationService.loadCommunicationSettings()) {
      registerReceivers();

      getSupportActionBar().setTitle(R.string.app_name);
      getSupportActionBar().setSubtitle(configurationService.getCommunicationSettings().getChannel());

      _dataListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
      dataListView.setAdapter(_dataListAdapter);
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

  //region IOmnipasteDataDisplay
  @Override
  public void omnipasteDataReceived(String data, Sender sender) {
    _dataListAdapter.insert(sender + ": " + data, 0);
    _dataListAdapter.notifyDataSetChanged();
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

    _omnipasteDataReceiver = new OmnipasteDataReceiver(this);
    IntentFilter dataReceiverIntentFilter = new IntentFilter(omnipasteDataReceived);
    registerReceiver(_omnipasteDataReceiver, dataReceiverIntentFilter);
  }

  private void unregisterReceivers() {
    unregisterReceiver(_statusChangedReceiver);
    unregisterReceiver(_connectivityReceiver);
    unregisterReceiver(_omnipasteDataReceiver);

    _statusChangedReceiver = null;
    _connectivityReceiver = null;
    _omnipasteDataReceiver = null;
  }
  //endregion
}
