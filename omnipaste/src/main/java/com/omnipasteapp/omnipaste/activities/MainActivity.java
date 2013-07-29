package com.omnipasteapp.omnipaste.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.adapters.ArrayAdapter2;
import com.omnipasteapp.omnipaste.backgroundServices.OmnipasteService;
import com.omnipasteapp.omnipaste.backgroundServices.OmnipasteService_;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.enums.Sender;
import com.omnipasteapp.omnipaste.services.IIntentService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends ActionBarActivity implements LogoutDialog.LogoutDialogListener {
  private static final String STATE_DATA = "stateData";

  private ArrayAdapter2 _dataListAdapter;
  private Messenger _omnipasteServiceMessenger;
  private String _status;

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
  public String appName;

  @StringRes
  public String textServiceConnected;

  @StringRes
  public String textServiceDisconnected;

  @StringRes
  public String textServiceConnecting;

  @ViewById
  public ListView dataListView;

  public Messenger messenger = new Messenger(new IncomingHandler());

  //endregion

  private ServiceConnection _connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      _omnipasteServiceMessenger = new Messenger(iBinder);
      MainActivity.this.sendMessage(OmnipasteService.MSG_CLIENT_CONNECTED);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
      MainActivity.this.sendMessage(OmnipasteService.MSG_CLIENT_DISCONNECTED);
      _omnipasteServiceMessenger = null;
    }
  };

  class IncomingHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case OmnipasteService.MSG_SERVICE_CONNECTED:
          setActionBarTitle(textServiceConnected);
          break;
        case OmnipasteService.MSG_SERVICE_DISCONNECTED:
          setActionBarTitle(textServiceDisconnected);
          break;
        case OmnipasteService.MSG_DATA_RECEIVED:
          Bundle data = msg.getData();

          if (data != null) {
            dataReceived(data.getString(OmnipasteService.EXTRA_CLIPBOARD_DATA), (Sender) data.getSerializable(OmnipasteService.EXTRA_CLIPBOARD_SENDER));
          }
        default:
          super.handleMessage(msg);
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);

    restoreInstanceState(savedInstanceState);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    unbindOmnipasteService();
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    for (int i = 0; i < _dataListAdapter.getCount(); i++) {
      data.add(_dataListAdapter.getItem(i));
    }
    savedInstanceState.putSerializable(STATE_DATA, data);

    super.onSaveInstanceState(savedInstanceState);
  }

  @AfterViews
  public void loadConfiguration() {
    if (configurationService.loadCommunicationSettings()) {
      setActionBarTitle(_status);
      getSupportActionBar().setSubtitle(configurationService.getCommunicationSettings().getChannel());

      setDataListAdapter();

      bindOmnipasteService();
    } else {
      intentService.startNewActivity(LoginActivity_.class);
    }
  }

  @UiThread
  public void setActionBarTitle(String status) {
    _status = status;
    getSupportActionBar().setTitle(appName + " (" + status + ")");
  }

  //region logout
  @OptionsItem
  public void logoutSelected() {
    LogoutDialog.create().show(getSupportFragmentManager(), LogoutDialog.TAG);
  }

  @Override
  public void onDialogPositiveClick(DialogFragment dialog) {
    // unbind
    unbindOmnipasteService();

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

  private void setDataListAdapter() {
    dataListView.setAdapter(_dataListAdapter);
  }

  private void bindOmnipasteService() {
    MainActivity.this.intentService.sendBroadcast(startOmnipasteService);
    bindService(new Intent(this, OmnipasteService_.class), _connection, Context.BIND_ABOVE_CLIENT);
  }

  private void dataReceived(String data, Sender sender) {
    HashMap<String, String> dataItem = new HashMap<String, String>();
    dataItem.put("title", sender.toString());
    dataItem.put("subtitle", data);

    _dataListAdapter.insert(dataItem, 0);
    _dataListAdapter.notifyDataSetChanged();
  }

  @SuppressWarnings("unchecked")
  private void restoreInstanceState(Bundle savedInstanceState) {
    _dataListAdapter = new ArrayAdapter2(this, android.R.layout.simple_list_item_2);
    _status = textServiceConnecting;

    if (savedInstanceState != null) {
      Serializable serializable = savedInstanceState.getSerializable(STATE_DATA);
      if (serializable != null) {
        ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>) serializable;

        for (HashMap<String, String> dataItem : data) {
          _dataListAdapter.add(dataItem);
        }

        _dataListAdapter.notifyDataSetChanged();
      }
    }
  }

  private void sendMessage(int code) {
    try {
      Message message = Message.obtain(null, code);

      if (message != null) {
        message.replyTo = messenger;
        _omnipasteServiceMessenger.send(message);
      }

    } catch (RemoteException e) {
      //TODO: replace with proper error handler
      e.printStackTrace();
    }
  }

  private void unbindOmnipasteService() {
    if (_omnipasteServiceMessenger == null) {
      return;
    }

    MainActivity.this.sendMessage(OmnipasteService.MSG_CLIENT_DISCONNECTED);
    unbindService(_connection);

    _omnipasteServiceMessenger = null;
  }

  //endregion
}
