package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.Clipping;
import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.ReplaySubject;

@Singleton
public class ClippingsPresenter extends Presenter<ClippingsPresenter.View> implements Observer<ClippingDto> {
  private final ReplaySubject<Clipping> clippingsSubject = ReplaySubject.create();
  private ClipboardSubscriber clipboardSubscriber;
  private Subscription clipboardSubscription;

  public interface View {
  }

  @Inject
  public ClippingsPresenter(ClipboardSubscriber clipboardSubscriber) {
    this.clipboardSubscriber = clipboardSubscriber;
  }

  @Override
  public void initialize() {
    if (clipboardSubscription != null) {
      return;
    }

    clipboardSubscription = clipboardSubscriber.subscribe(this);
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
    clipboardSubscription.unsubscribe();
    clipboardSubscription = null;
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(ClippingDto clippingDto) {
    clippingsSubject.onNext(Clipping.add(clippingDto));
  }

  public Observable<Clipping> getObservable() {
    return clippingsSubject;
  }

  public void remove(ClippingDto clippingDto) {
    clippingsSubject.onNext(Clipping.remove(clippingDto));
  }
}
