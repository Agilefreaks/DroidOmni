package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import rx.subjects.PublishSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LocalFragmentTest extends TestCase {
  LocalFragment localFragment;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    localFragment = new LocalFragment();
  }

  public void testWillAddLocalClippings() throws Exception {
    PublishSubject<ClippingDto> publishSubject = PublishSubject.create();
    ClippingDto localClipping = new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.local);
    localFragment.observer(publishSubject);

    publishSubject.onNext(new ClippingDto().setClippingProvider(ClippingDto.ClippingProvider.cloud));
    publishSubject.onNext(localClipping);

    assertThat(localFragment.getActualListAdapter().getCount(), is(1));
    assertThat(localFragment.getActualListAdapter().getItem(0), is(localClipping));
  }
}
