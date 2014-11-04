package com.omnipaste.omnicommon.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class Schedulable {
  protected Scheduler scheduler = Schedulers.io();
  protected Scheduler observeOnScheduler = AndroidSchedulers.mainThread();

  public void setScheduler(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  public void setObserveOnScheduler(Scheduler observeOnScheduler) {
    this.observeOnScheduler = observeOnScheduler;
  }
}
