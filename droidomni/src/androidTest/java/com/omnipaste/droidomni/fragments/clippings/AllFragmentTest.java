package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import rx.subjects.PublishSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AllFragmentTest extends TestCase {
  AllFragment allFragment;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    allFragment = new AllFragment();
  }

  public void testWillAddClippingsNoMatterWhatProvider() throws Exception {
    PublishSubject<ClippingDto> publishSubject = PublishSubject.create();
    allFragment.observe(publishSubject);

    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.cloud));
    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.local));
    
    assertThat(allFragment.getActualListAdapter().getCount(), is(2));
  }
}
