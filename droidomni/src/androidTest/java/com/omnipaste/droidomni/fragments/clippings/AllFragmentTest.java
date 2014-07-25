package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.droidomni.adapters.IClippingAdapter;
import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AllFragmentTest extends TestCase {
  AllFragment allFragment;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    allFragment = AllFragment_.builder().build();
  }

  public void testWillAddClippingsNoMatterWhatProvider() throws Exception {
    IClippingAdapter mockClippingsAdapter = mock(IClippingAdapter.class);
    allFragment.setListAdapter(mockClippingsAdapter);
    PublishSubject<ClippingDto> publishSubject = PublishSubject.create();
    allFragment.observe(publishSubject);

    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.cloud));
    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.local));

    verify(mockClippingsAdapter, times(2)).add(any(ClippingDto.class));
  }
}
