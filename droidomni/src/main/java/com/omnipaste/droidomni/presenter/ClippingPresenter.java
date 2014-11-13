package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.ClippingAdapter;
import com.omnipaste.droidomni.service.SmartActionService;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

@Singleton
public class ClippingPresenter extends Presenter<ClippingPresenter.View> implements Observer<ClippingDto> {

  private final ClippingAdapter clippingAdapter;
  private SmartActionService smartActionService;

  public interface View {
  }

  @Inject
  public ClippingPresenter(
      ClipboardSubscriber clipboardSubscriber,
      ClippingAdapter clippingAdapter,
      SmartActionService smartActionService
  ) {
    this.smartActionService = smartActionService;
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

  @Override
  public void onNext(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);
  }

  public ClippingAdapter getClippingAdapter() {
    return clippingAdapter;
  }

  public void remove(ClippingDto item) {
    clippingAdapter.remove(item);
  }

  public void smartAction(ClippingDto item) {
    smartActionService.run(item);
  }
}
