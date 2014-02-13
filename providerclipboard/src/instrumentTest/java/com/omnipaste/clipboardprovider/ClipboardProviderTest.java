package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.ILocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;

import junit.framework.TestCase;

import dagger.Lazy;
import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClipboardProviderTest extends TestCase {
  private ClipboardProvider clipboardProvider;

  @SuppressWarnings("unchecked")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    clipboardProvider = new ClipboardProvider();

    Lazy mockLazyLocal = mock(Lazy.class);
    ILocalClipboardManager mockLocal = mock(ILocalClipboardManager.class);
    when(mockLazyLocal.get()).thenReturn(mockLocal);
    when(mockLocal.getObservable()).thenReturn((Observable) Observable.empty());
    clipboardProvider.localClipboardManager = mockLazyLocal;

    Lazy mockLazyOmni = mock(Lazy.class);
    IOmniClipboardManager mockOmni = mock(IOmniClipboardManager.class);
    when(mockLazyOmni.get()).thenReturn(mockOmni);
    when(mockOmni.getObservable()).thenReturn((Observable) Observable.empty());
    clipboardProvider.omniClipboardManager = mockLazyOmni;
  }

  public void testEnableAlwaysReturnsAnObservable() throws Exception {
    assertThat(clipboardProvider.subscribe("channel", "identifier"), isA(Observable.class));
  }

  public void testGetObservableWillReturnTheSameInstanceOnMultipleCalls() throws Exception {
    assertThat(clipboardProvider.subscribe("channel", "identifier"), sameInstance(clipboardProvider.subscribe("channel", "identifier")));
  }
}
