package com.omnipaste.droidomni.adapter;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ClippingAdapterTest extends TestCase {
  public void testAddOnlyAdds42Items() throws Exception {
    ClippingAdapter clippingAdapter = ClippingAdapter.build();

    for(int i = 0; i < 52; i++) {
      clippingAdapter.add(new ClippingDto());
    }

    assertThat(clippingAdapter.getCount(), is(42));
  }
}