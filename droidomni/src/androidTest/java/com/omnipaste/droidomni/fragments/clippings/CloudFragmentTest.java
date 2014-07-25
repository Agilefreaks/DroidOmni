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

public class CloudFragmentTest extends TestCase {
  CloudFragment cloudFragment;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    cloudFragment = CloudFragment_.builder().build();
  }

  public void testWillAddCloudClippings() throws Exception {
    ClippingDto cloudClipping = new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.cloud);
    IClippingAdapter mockClippingsAdapter = mock(IClippingAdapter.class);
    cloudFragment.setListAdapter(mockClippingsAdapter);
    PublishSubject<ClippingDto> publishSubject = PublishSubject.create();
    cloudFragment.observe(publishSubject);

    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.local));
    publishSubject.onNext(cloudClipping);

    verify(mockClippingsAdapter, times(1)).add(cloudClipping);
  }
}
