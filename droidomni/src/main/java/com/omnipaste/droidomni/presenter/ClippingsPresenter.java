package com.omnipaste.droidomni.presenter;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.domain.Clipping;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

@Singleton
public class ClippingsPresenter extends Presenter<ClippingsPresenter.View> implements Observer<ClippingDto> {
  private final ClipboardProvider clipboardProvider;
  private PublishSubject<Clipping> clippingsSubject;
  private Subscription clipboardSubscription;

  public interface View {
  }

  @Inject
  public ClippingsPresenter(ClipboardProvider clipboardProvider) {
    this.clipboardProvider = clipboardProvider;
  }

  @Override
  public void initialize() {
    if (clipboardSubscription != null) {
      return;
    }

    clippingsSubject = PublishSubject.create();
    clipboardSubscription = clipboardProvider
        .getObservable()
        .observeOn(observeOnScheduler)
        .subscribe(this);
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
//     we need a way to persist the info
    clippingsSubject.onCompleted();

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

  public void remove(ClippingDto clippingDto) {
    clippingsSubject.onNext(Clipping.remove(clippingDto));
  }

  public Observable<Clipping> getObservable() {
    return clippingsSubject;
  }

  public void showSamples() {
    ClippingDto localClipping = new ClippingDto();
    localClipping.setContent(getString(R.string.tutorial_local_clipping));
    localClipping.setClippingProvider(ClippingDto.ClippingProvider.LOCAL);
    clippingsSubject.onNext(Clipping.add(localClipping));

    ClippingDto cloudClipping = new ClippingDto();
    cloudClipping.setContent(getString(R.string.tutorial_cloud_clipping));
    cloudClipping.setClippingProvider(ClippingDto.ClippingProvider.CLOUD);
    clippingsSubject.onNext(Clipping.add(cloudClipping));

    ClippingDto phoneNumberClipping = new ClippingDto();
    phoneNumberClipping.setContent(getString(R.string.tutorial_phone));
    phoneNumberClipping.setClippingProvider(ClippingDto.ClippingProvider.CLOUD);
    phoneNumberClipping.setType(ClippingDto.ClippingType.PHONE_NUMBER);
    clippingsSubject.onNext(Clipping.add(phoneNumberClipping));

    ClippingDto addressClipping = new ClippingDto();
    addressClipping.setContent(getString(R.string.tutorial_address));
    addressClipping.setClippingProvider(ClippingDto.ClippingProvider.CLOUD);
    addressClipping.setType(ClippingDto.ClippingType.ADDRESS);
    clippingsSubject.onNext(Clipping.add(addressClipping));
  }

  private String getString(int resId) {
    return DroidOmniApplication.getAppContext().getString(resId);
  }
}
