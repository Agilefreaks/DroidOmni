package com.omnipaste.droidomni.presenter;

import android.content.Context;

import com.omnipaste.omnicommon.rx.Schedulable;

public abstract class Presenter<T> extends Schedulable {
  private T view;

  protected Context context;

  protected Presenter(Context activityContext) {
    this.context = activityContext;
  }

  public void setView(T view) {
    this.view = view;
  }

  public T getView() {
    return view;
  }

  /**
   * Called when the presenter is initialized, this method represents the start of the presenter
   * lifecycle.
   */
  public abstract void initialize();

  /**
   * Called when the presenter is resumed. After the initialization and when the presenter comes
   * from a pause state.
   */
  public abstract void resume();

  /**
   * Called when the presenter is paused.
   */
  public abstract void pause();
}
