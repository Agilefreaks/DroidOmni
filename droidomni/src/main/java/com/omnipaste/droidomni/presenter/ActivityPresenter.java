package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ClippingAdapter;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

@Singleton
public class ActivityPresenter extends FragmentPresenter implements Observer<ClippingDto> {

  private final ClippingAdapter clippingAdapter;

  @Inject
  public ActivityPresenter(
      ClipboardSubscriber clipboardSubscriber,
      ClippingAdapter clippingAdapter
  ) {
    clipboardSubscriber.subscribe(this);
    this.clippingAdapter = clippingAdapter;
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

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override public void onNext(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);
  }

  public ClippingAdapter getClippingAdapter() {
    return clippingAdapter;
  }
}
