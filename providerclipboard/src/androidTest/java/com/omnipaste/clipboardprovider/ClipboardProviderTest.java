package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.ILocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.IOmniClipboardManager;
import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import java.util.Arrays;

import dagger.Lazy;
import rx.Observable;
import rx.observers.TestObserver;
import rx.schedulers.TestScheduler;
import rx.subjects.TestSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClipboardProviderTest extends TestCase {
  private final TestScheduler testScheduler = new TestScheduler();
  private final TestSubject<ClippingDto> omniObservable = TestSubject.create(testScheduler);
  private final TestSubject<ClippingDto> localObservable = TestSubject.create(testScheduler);

  private ClipboardProvider clipboardProvider;

  @SuppressWarnings("unchecked")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    clipboardProvider = new ClipboardProvider();

    Lazy mockLazyLocal = mock(Lazy.class);
    ILocalClipboardManager mockLocalClipboardManager = mock(ILocalClipboardManager.class);
    when(mockLazyLocal.get()).thenReturn(mockLocalClipboardManager);
    when(mockLocalClipboardManager.getObservable()).thenReturn(localObservable);
    clipboardProvider.localClipboardManager = mockLazyLocal;

    Lazy mockLazyOmni = mock(Lazy.class);
    IOmniClipboardManager mockOmniClipboardManager = mock(IOmniClipboardManager.class);
    when(mockLazyOmni.get()).thenReturn(mockOmniClipboardManager);
    when(mockOmniClipboardManager.getObservable()).thenReturn(omniObservable);
    clipboardProvider.omniClipboardManager = mockLazyOmni;
  }

  public void testEnableAlwaysReturnsAnObservable() throws Exception {
    assertThat(clipboardProvider.init("identifier"), isA(Observable.class));
  }

  public void testGetObservableWillReturnTheSameInstanceOnMultipleCalls() throws Exception {
    assertThat(clipboardProvider.init("identifier"), sameInstance(clipboardProvider.init("identifier")));
  }

  public void testDuplicatesAreIgnoredOnLocal() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setContent("the one");
    ClippingDto otherClipping = new ClippingDto().setContent("the second");
    TestObserver<ClippingDto> testObserver = new TestObserver<>();

    clipboardProvider.init("42").subscribe(testObserver);
    localObservable.onNext(clippingDto);
    localObservable.onNext(clippingDto);
    localObservable.onNext(otherClipping);
    testScheduler.triggerActions();

    testObserver.assertReceivedOnNext(Arrays.asList(clippingDto, otherClipping));
  }

  public void testDuplicatesAreIgnoredOnOmni() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setContent("the one");
    ClippingDto otherClipping = new ClippingDto().setContent("the second");
    TestObserver<ClippingDto> testObserver = new TestObserver<>();

    clipboardProvider.init("42").subscribe(testObserver);
    omniObservable.onNext(clippingDto);
    omniObservable.onNext(clippingDto);
    omniObservable.onNext(otherClipping);
    testScheduler.triggerActions();

    testObserver.assertReceivedOnNext(Arrays.asList(clippingDto, otherClipping));
  }
}
