package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.droidomni.adapters.IClippingAdapter;
import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import rx.subjects.PublishSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LocalFragmentTest extends TestCase {
  LocalFragment localFragment;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    localFragment = LocalFragment_.builder().build();
  }

  public void testWillAddLocalClippings() throws Exception {
    ClippingDto localClipping = new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.local);
    IClippingAdapter mockClippingsAdapter = mock(IClippingAdapter.class);
    localFragment.setListAdapter(mockClippingsAdapter);
    PublishSubject<ClippingDto> publishSubject = PublishSubject.create();
    localFragment.observe(publishSubject);

    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.cloud));
    publishSubject.onNext(localClipping);

    verify(mockClippingsAdapter, times(1)).add(localClipping);
  }
}
