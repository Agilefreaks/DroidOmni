package com.omnipasteapp.omnipaste.backgroundServices;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.enums.Sender;

import java.util.ArrayList;

@EService
public class OmnipasteService extends Service implements ICanReceiveData {
  public static final String EXTRA_CLIPBOARD_SENDER = "clipboardSender";
  public static final String EXTRA_CLIPBOARD_DATA = "clipboardData";

  public static final int MSG_CLIENT_CONNECTED = 1;
  public static final int MSG_CLIENT_DISCONNECTED = 2;
  public static final int MSG_SERVICE_CONNECTED = 3;
  public static final int MSG_SERVICE_DISCONNECTED = 4;
  public static final int MSG_DATA_RECEIVED = 5;

  private ArrayList<Messenger> _clients = new ArrayList<Messenger>();
  private OmniServiceReceiver _omniOmniServiceReceiver = new OmniServiceReceiver();

  //region public properties

  public final Messenger _messenger = new Messenger(new IncomingHandler());

  public IOmniService omniService;

  @SystemService
  public NotificationManager notificationManager;

  @StringRes
  public String omnipasteServiceStatusChanged;

  @StringRes
  public String omnipasteDataReceived;

  @StringRes
  public String startOmniService;

  @StringRes
  public String stopOmniService;

  //endregion

  class IncomingHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_CLIENT_CONNECTED:
          _clients.add(msg.replyTo);

          // send state to the new client
          if (OmnipasteService.this.omniService != null) {
            OmnipasteService.this.notifyStarted();
          }
          break;
        case MSG_CLIENT_DISCONNECTED:
          _clients.remove(msg.replyTo);
          break;
        default:
          super.handleMessage(msg);
      }
    }
  }

  class OmniServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      if (startOmniService.equals(intent.getAction())) {
        OmnipasteService.this.startOmniService();
      } else {
        OmnipasteService.this.stopOmniService();
      }
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();

    IntentFilter filter = new IntentFilter();
    filter.addAction(startOmniService);
    filter.addAction(stopOmniService);
    registerReceiver(_omniOmniServiceReceiver, filter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    unregisterReceiver(_omniOmniServiceReceiver);
    stopOmniService();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return _messenger.getBinder();
  }

  @Override
  public synchronized int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);

    startOmniService();

    // We want this service to continue running until it is explicitly
    // stopped, so return sticky.
    return START_STICKY;
  }

  public void startOmniService() {
    if (omniService != null) {
      return;
    }

    omniService = OmnipasteApplication.get(IOmniService.class);

    try {
      omniService.start();
      omniService.addListener(this);
    } catch (InterruptedException e) {
      //TODO: replace with proper error handler
      e.printStackTrace();
    }

    notifyStarted();
  }

  public void stopOmniService() {
    omniService.removeListener(this);
    omniService.stop();
    omniService = null;

    notifyStopped();
  }

  //region ICanReceiveData

  @Override
  public void dataReceived(IClipboardData clipboardData) {
    Bundle bundle = new Bundle();
    Sender sender;

    if (clipboardData.getSender() instanceof ILocalClipboard) {
      sender = Sender.Local;
    } else {
      sender = Sender.Omni;
    }

    bundle.putSerializable(EXTRA_CLIPBOARD_SENDER, sender);
    bundle.putString(EXTRA_CLIPBOARD_DATA, clipboardData.getData());

    sendMessage(MSG_DATA_RECEIVED, bundle);
  }

  //endregion

  private void notifyStarted() {
    sendMessage(MSG_SERVICE_CONNECTED);
  }

  private void notifyStopped() {
    sendMessage(MSG_SERVICE_DISCONNECTED);
  }

  private void sendMessage(int code) {
    sendMessage(code, null);
  }

  private void sendMessage(int code, Bundle bundle) {
    for (Messenger client : _clients) {
      try {
        Message message = Message.obtain(null, code);

        if (message != null) {
          message.setData(bundle);
          client.send(message);
        }
      } catch (RemoteException e) {
        // TODO replace with proper error handler
        e.printStackTrace();
      }
    }
  }
}