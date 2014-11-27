package com.omnipaste.droidomni.adapter;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ActivityAdapterTest extends TestCase {
  public void testAddOnlyAdds42Items() throws Exception {
    ActivityAdapter activityAdapter = ActivityAdapter.build();

    for(int i = 0; i < 52; i++) {
      activityAdapter.add(new ClippingDto());
    }

    assertThat(activityAdapter.getCount(), is(42));
  }
}