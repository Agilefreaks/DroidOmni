package com.omnipasteapp.omnipaste.services;

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
import com.omnipasteapp.clipboardprovider.ClipboardProvider;
import com.omnipasteapp.omniapi.OmniApi;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnimessaging.IMessagingService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;

import java.util.ArrayList;

import javax.inject.Inject;

@EService
public class OmnipasteService extends Service implements ICanReceiveData {
  public static final String EXTRA_CLIPPING = "clipboardData";

  public static final int MSG_CLIENT_CONNECTED = 1;
  public static final int MSG_CLIENT_DISCONNECTED = 2;
  public static final int MSG_SERVICE_CONNECTED = 3;
  public static final int MSG_SERVICE_DISCONNECTED = 4;
  public static final int MSG_DATA_RECEIVED = 5;

  private ArrayList<Messenger> clients = new ArrayList<>();
  private OmniServiceReceiver omniServiceReceiver = new OmniServiceReceiver();

  //region public properties

  public final Messenger messenger = new Messenger(new IncomingHandler());

  @Inject
  public IClipboardProvider clipboardProvider;

  @Inject
  public IMessagingService messagingService;

  @Inject
  IConfigurationService configurationService;

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

  @StringRes
  public String appName;

  @StringRes
  public String textServiceConnected;

  @StringRes
  public String textServiceDisconnected;

  //endregion

  class IncomingHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_CLIENT_CONNECTED:
          clients.add(msg.replyTo);

          // send state to the new client
          if (OmnipasteService.this.clipboardProvider != null) {
            OmnipasteService.this.notifyStarted();
          }
          break;
        case MSG_CLIENT_DISCONNECTED:
          clients.remove(msg.replyTo);
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

    OmnipasteApplication.inject(this);

    IntentFilter filter = new IntentFilter();
    filter.addAction(startOmniService);
    filter.addAction(stopOmniService);
    registerReceiver(omniServiceReceiver, filter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    unregisterReceiver(omniServiceReceiver);
    omniServiceReceiver = null;
    stopOmniService();

    unnotifyUser();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return messenger.getBinder();
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
    try {
      String channel = configurationService.getCommunicationSettings().getChannel();

      // init the messaging service
      messagingService.connect(channel);

      // set api key
      OmniApi.setApiKey(channel);

      // init the clipboard provider
      clipboardProvider = OmnipasteApplication.get(ClipboardProvider.class);
      clipboardProvider.start();
      clipboardProvider.addListener(this);

      messagingService.setClipboardProvider(clipboardProvider);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    notifyStarted();
  }

  public void stopOmniService() {
    messagingService.disconnect(configurationService.getCommunicationSettings().getChannel());

    clipboardProvider.removeListener(this);
    clipboardProvider.stop();
    clipboardProvider = null;

    notifyStopped();
  }

  //region ICanReceiveData

  @Override
  public void dataReceived(Clipping clipping) {
    Bundle bundle = new Bundle();
    bundle.putParcelable(EXTRA_CLIPPING, clipping);

    sendMessage(MSG_DATA_RECEIVED, bundle);

    notifyClipping(clipping);
  }

  //endregion

  //region Notifications

  private void notifyUser(String text) {
    Intent notificationService = new Intent(this, NotificationService_.class);
    notificationService.putExtra(NotificationService.TEXT, text);
    notificationService.putExtra(NotificationService.ACTION, NotificationService.ActionType.Create);
    startService(notificationService);
  }

  private void unnotifyUser() {
    Intent notificationService = new Intent(this, NotificationService_.class);
    notificationService.putExtra(NotificationService.ACTION, NotificationService.ActionType.Destroy);
    startService(notificationService);
  }

  private void notifyClipping(Clipping clipping) {
    Intent notificationService = new Intent(this, NotificationService_.class);
    notificationService.putExtra(NotificationService.CLIPPING, clipping);
    notificationService.putExtra(NotificationService.ACTION, NotificationService.ActionType.Update);
    startService(notificationService);
  }

  //endregion

  private void notifyStarted() {
    sendMessage(MSG_SERVICE_CONNECTED);
    notifyUser(textServiceConnected);
  }

  private void notifyStopped() {
    sendMessage(MSG_SERVICE_DISCONNECTED);
    notifyUser(textServiceDisconnected);
  }

  private void sendMessage(int code) {
    sendMessage(code, null);
  }

  private void sendMessage(int code, Bundle bundle) {
    for (Messenger client : clients) {
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