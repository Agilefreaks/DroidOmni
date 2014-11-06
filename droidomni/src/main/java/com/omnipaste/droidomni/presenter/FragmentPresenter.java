package com.omnipaste.droidomni.presenter;

import android.app.Activity;

public abstract class FragmentPresenter<TView> extends Presenter<TView> {
  public abstract void attachActivity(Activity activity);
}
