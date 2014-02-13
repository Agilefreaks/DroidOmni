package com.omnipaste.clipboardprovider.androidclipboard;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.dto.ClippingDto;

import org.hamcrest.core.IsInstanceOf;

import rx.Observable;
import rx.Observer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LocalClipboardManagerTest extends InstrumentationTestCase {
  private LocalClipboardManager localClipboardManager;
  private ClipboardManager clipboardManager;

  @SuppressWarnings("ConstantConditions")
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    clipboardManager = mock(ClipboardManager.class);
    localClipboardManager = new LocalClipboardManager(clipboardManager);
  }

  public void testGetObservableWillReturnAInstanceOfAObserver() throws Exception {
    assertThat(localClipboardManager.getObservable(), IsInstanceOf.any(Observable.class));
  }

  @SuppressWarnings("unchecked")
  public void testGePrimaryClipWillReturnThePrimaryClip() {
    Observer observer = mock(Observer.class);
    when(clipboardManager.getPrimaryClip()).thenReturn(ClipData.newPlainText("label",  "some text"));

    localClipboardManager.getPrimaryClip("some@channel").subscribe(observer);

    verify(observer, times(1)).onNext(isA(ClippingDto.class));
  }

  public void testSetPrimaryClipWillCallSetPrimaryClipOnClipboardManager() {
    localClipboardManager.setPrimaryClip("channel@to", new ClippingDto());

    verify(clipboardManager).setPrimaryClip(isA(ClipData.class));
  }

  @SuppressWarnings("unchecked")
  public void testOnPrimaryClipChangedFiresOnNext() {
    Observer observer = mock(Observer.class);
    localClipboardManager.getObservable().subscribe(observer);

    localClipboardManager.onPrimaryClipChanged();

    verify(observer, times(1)).onNext("");
  }

  public void testGetObserverWillReturnTheSameInstanceMultipleTimes() throws Exception {
    assertThat(localClipboardManager.getObservable(), sameInstance(localClipboardManager.getObservable()));
  }
}
