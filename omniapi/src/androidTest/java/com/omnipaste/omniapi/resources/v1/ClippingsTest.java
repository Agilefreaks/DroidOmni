package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omniapi.OmniApiTestCase;
import com.omnipaste.omnicommon.dto.ClippingDto;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class ClippingsTest extends OmniApiTestCase {
  private Clippings clippings;

  public void setUp() throws Exception {
    super.setUp();

    clippings = omniApiV1.clippings();
  }

  public void testLastWillReturnAnObservable() throws Exception {
    assertThat(clippings.last(), instanceOf(Observable.class));
  }

  public void testCreateWillReturnAnObservable() throws Exception {
    assertThat(clippings.create(new ClippingDto()), instanceOf(Observable.class));
  }
}
