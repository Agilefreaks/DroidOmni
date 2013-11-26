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
import android.os.Parcelable;
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
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.adapters.ArrayAdapter2;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.services.IIntentHelper;
import com.omnipasteapp.omnipaste.services.OmnipasteService;
import com.omnipasteapp.omnipaste.services.OmnipasteService_;

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
  public IIntentHelper intentService;

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
            dataReceived((Clipping) data.getParcelable(OmnipasteService.EXTRA_CLIPPING));
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

    _dataListAdapter = new ArrayAdapter2(this, android.R.layout.simple_list_item_2);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    unbindOmnipasteService();
  }

  @AfterViews
  public void loadConfiguration() {
    setActionBarTitle(_status);
    getSupportActionBar().setSubtitle(configurationService.getCommunicationSettings().getChannel());

    setDataListAdapter();

    bindOmnipasteService();
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

  //region state management

  @Override
  protected void onSaveInstanceState(Bundle savedInstanceState) {
    Clipping[] data = new Clipping[_dataListAdapter.getCount()];
    for (int i = 0; i < _dataListAdapter.getCount(); i++) {
      data[i] = _dataListAdapter.getItem(i);
    }
    savedInstanceState.putParcelableArray(STATE_DATA, data);

    super.onSaveInstanceState(savedInstanceState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    _dataListAdapter = new ArrayAdapter2(this, android.R.layout.simple_list_item_2);
    _status = textServiceConnecting;

    if (savedInstanceState != null) {
      Parcelable[] parcelableArray = savedInstanceState.getParcelableArray(STATE_DATA);
      if (parcelableArray != null && parcelableArray.length > 0) {
        for (Parcelable parcelable : parcelableArray) {
          _dataListAdapter.add((Clipping) parcelable);
        }

        setDataListAdapter();
        _dataListAdapter.notifyDataSetChanged();
      }
    }
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

  private void dataReceived(Clipping clipping) {
    _dataListAdapter.insert(clipping, 0);
    _dataListAdapter.notifyDataSetChanged();
  }

  private void sendMessage(int code) {
    try {
      Message message = Message.obtain(null, code);

      if (message != null) {
        message.replyTo = messenger;
        _omnipasteServiceMessenger.send(message);
      }

    } catch (RemoteException e) {
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
