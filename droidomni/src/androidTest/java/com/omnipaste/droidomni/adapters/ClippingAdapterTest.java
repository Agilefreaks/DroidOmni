package com.omnipaste.droidomni.adapters;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ClippingAdapterTest extends TestCase {
  public void testAddOnlyAdds42Items() throws Exception {
    ClippingAdapter clippingAdapter = new ClippingAdapter();

    for(int i = 0; i < 44; i++) {
      clippingAdapter.add(new ClippingDto());
    }

    assertThat(clippingAdapter.getCount(), is(42));
  }
}