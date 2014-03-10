package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import rx.subjects.PublishSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CloudFragmentTest extends TestCase {
  CloudFragment cloudFragment;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    cloudFragment = new CloudFragment();
  }

  public void testWillAddCloudClippings() throws Exception {
    PublishSubject<ClippingDto> publishSubject = PublishSubject.create();
    ClippingDto cloudClipping = new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.cloud);
    cloudFragment.observer(publishSubject);

    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.local));
    publishSubject.onNext(cloudClipping);

    assertThat(cloudFragment.getActualListAdapter().getCount(), is(1));
    assertThat(cloudFragment.getActualListAdapter().getItem(0), is(cloudClipping));
  }
}
