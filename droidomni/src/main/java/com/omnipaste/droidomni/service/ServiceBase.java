package com.omnipaste.droidomni.service;

import com.omnipaste.omnicommon.rx.Schedulable;

public abstract class ServiceBase extends Schedulable {
  public abstract void start();

  public abstract void stop();
}
