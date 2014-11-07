package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import java.lang.ref.WeakReference;

public abstract class FragmentPresenter<TView> extends Presenter<TView> {
  private WeakReference<Activity>  activity;

  public void attachActivity(Activity activity) {
    this.activity = new WeakReference<>(activity);
  }

  public Activity getActivity() {
    return activity.get();
  }
}
