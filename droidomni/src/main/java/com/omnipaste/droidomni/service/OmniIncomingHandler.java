package com.omnipaste.droidomni.service;

import android.os.Handler;
import android.os.Message;

import rx.subjects.PublishSubject;

public class OmniIncomingHandler extends Handler {
  public static final int MSG_CREATE_ERROR = 3;
  public static final int MSG_STARTED = 4;
  public static final int MSG_STOPPED = 5;

  private final PublishSubject connectionSubject;

  public OmniIncomingHandler(PublishSubject connectionSubject) {
    this.connectionSubject = connectionSubject;
  }

  @Override
  public void handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_STARTED:
      case MSG_STOPPED:
        connectionSubject.onCompleted();
        break;
      case MSG_CREATE_ERROR:
        connectionSubject.onError((Throwable) msg.obj);
        break;
      default:
        super.handleMessage(msg);
    }
  }
}