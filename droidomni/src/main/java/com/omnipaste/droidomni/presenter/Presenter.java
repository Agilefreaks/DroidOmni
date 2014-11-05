package com.omnipaste.droidomni.presenter;

import com.omnipaste.omnicommon.rx.Schedulable;

import java.lang.ref.WeakReference;

public abstract class Presenter<TView> extends Schedulable {
  private WeakReference<TView> view;

  public void attachView(TView view) {
    this.view = new WeakReference<>(view);
  }

  public TView getView() {
    return view != null ? view.get() : null;
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
