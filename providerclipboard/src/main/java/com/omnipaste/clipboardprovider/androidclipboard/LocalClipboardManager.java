package com.omnipaste.clipboardprovider.androidclipboard;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;

import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LocalClipboardManager implements ILocalClipboardManager, ClipboardManager.OnPrimaryClipChangedListener {
  private PublishSubject<String> localClipboardSubject;
  private ClipboardManager clipboardManager;
  private boolean skipNext = false;

  @Inject
  public LocalClipboardManager(ClipboardManager clipboardManager) {
    this.clipboardManager = clipboardManager;
    this.clipboardManager.addPrimaryClipChangedListener(this);

    localClipboardSubject = PublishSubject.create();
  }

  @Override
  public Observable<String> getObservable() {
    return localClipboardSubject;
  }

  @Override
  public Observable<ClippingDto> getPrimaryClip(String channel) {
    return Observable.create(new Observable.OnSubscribe<ClippingDto>() {
      @SuppressWarnings("ConstantConditions")
      @Override
      public void call(Subscriber<? super ClippingDto> observer) {
        if (hasPrimaryClip()) {
          ClippingDto clippingDto = new ClippingDto();
          clippingDto.setContent(getPrimaryClip().getItemAt(0).getText().toString());
          clippingDto.setIdentifier("");
          observer.onNext(clippingDto);
        }

        observer.onCompleted();
      }

      private ClipData getPrimaryClip() {
        return clipboardManager.getPrimaryClip();
      }
    });
  }

  @Override
  public ClippingDto setPrimaryClip(String channel, ClippingDto clippingDto) {
    skipNext = true;
    clipboardManager.setPrimaryClip(ClipData.newPlainText("", clippingDto.getContent()));

    return new ClippingDto(clippingDto).setClippingProvider(ClippingDto.ClippingProvider.cloud);
  }

  @Override
  public void onPrimaryClipChanged() {
    if (!skipNext && clipboardManager.hasPrimaryClip()) {
      localClipboardSubject.onNext("");
    }

    skipNext = false;
  }

  public Boolean hasPrimaryClip() {
    ClipData primaryClip = clipboardManager.getPrimaryClip();
    return clipboardManager.hasPrimaryClip() && primaryClip != null && primaryClip.getItemAt(0) != null && primaryClip.getItemAt(0).getText() != null;
  }
}
