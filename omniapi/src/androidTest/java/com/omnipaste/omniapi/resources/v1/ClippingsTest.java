package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class ClippingsTest extends TestCase {
  private Clippings clippings;

  public void setUp() throws Exception {
    super.setUp();

    clippings = new Clippings(new AccessTokenDto("access"), "http://test.omnipasteapp.com/api");
  }

  public void testLastWillReturnAnObservable() throws Exception {
    assertThat(clippings.last(), instanceOf(Observable.class));
  }

  public void testCreateWillReturnAnObservable() throws Exception {
    assertThat(clippings.create(new ClippingDto()), instanceOf(Observable.class));
  }
}
