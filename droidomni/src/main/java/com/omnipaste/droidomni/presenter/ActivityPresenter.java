package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ClippingAdapter;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

@Singleton
public class ActivityPresenter extends FragmentPresenter {

  private ClippingPresenter clippingPresenter;

  @Inject
  public ActivityPresenter(ClippingPresenter clippingPresenter) {
    this.clippingPresenter = clippingPresenter;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  public ClippingAdapter getClippingAdapter() {
    return clippingPresenter.getClippingAdapter();
  }
}
