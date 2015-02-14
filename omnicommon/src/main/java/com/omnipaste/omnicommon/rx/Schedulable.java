package com.omnipaste.omnicommon.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class Schedulable {
  private Scheduler scheduler;
  private Scheduler observeOnScheduler;

  public void setScheduler(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  public Scheduler getScheduler() {
    if (scheduler == null) {
      scheduler = Schedulers.io();
    }
    return scheduler;
  }

  public void setObserveOnScheduler(Scheduler observeOnScheduler) {
    this.observeOnScheduler = observeOnScheduler;
  }

  public Scheduler getObserveOnScheduler() {
    if (observeOnScheduler == null) {
      observeOnScheduler = AndroidSchedulers.mainThread();
    }
    return observeOnScheduler;
  }
}
