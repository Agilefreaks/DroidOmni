package com.omnipaste.clipboardprovider.androidclipboard;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;

import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LocalClipboardManager implements ILocalClipboardManager, ClipboardManager.OnPrimaryClipChangedListener {
  private PublishSubject<ClippingDto> localClipboardSubject;
  private ClipboardManager clipboardManager;
  private boolean skipNext = false;

  @Inject
  public LocalClipboardManager(ClipboardManager clipboardManager) {
    this.clipboardManager = clipboardManager;
    this.clipboardManager.addPrimaryClipChangedListener(this);

    localClipboardSubject = PublishSubject.create();
  }

  @Override
  public Observable<ClippingDto> getObservable() {
    return localClipboardSubject;
  }

  @Override
  public ClippingDto setPrimaryClip(ClippingDto clippingDto) {
    skipNext = true;
    clipboardManager.setPrimaryClip(ClipData.newPlainText("", clippingDto.getContent()));

    return clippingDto;
  }

  @Override
  public void onPrimaryClipChanged() {
    if (!skipNext) {
      getPrimaryClip().subscribe(new Action1<ClippingDto>() {
        @Override
        public void call(ClippingDto clippingDto) {
          localClipboardSubject.onNext(clippingDto);
        }
      });
    }

    skipNext = false;
  }

  public Boolean hasPrimaryClip() {
    ClipData primaryClip = clipboardManager.getPrimaryClip();
    return clipboardManager.hasPrimaryClip() && primaryClip != null && primaryClip.getItemAt(0) != null && primaryClip.getItemAt(0).getText() != null;
  }

  public Observable<ClippingDto> getPrimaryClip() {
    return Observable.create(new Observable.OnSubscribe<ClippingDto>() {
      @SuppressWarnings("ConstantConditions")
      @Override
      public void call(Subscriber<? super ClippingDto> observer) {
        if (hasPrimaryClip()) {
          ClippingDto clippingDto = new ClippingDto();
          clippingDto.setContent(getPrimaryClip().getItemAt(0).getText().toString());
          clippingDto.setIdentifier("");
          clippingDto.setClippingProvider(ClippingDto.ClippingProvider.LOCAL);
          observer.onNext(clippingDto);
        }

        observer.onCompleted();
      }

      private ClipData getPrimaryClip() {
        return clipboardManager.getPrimaryClip();
      }
    });
  }
}
