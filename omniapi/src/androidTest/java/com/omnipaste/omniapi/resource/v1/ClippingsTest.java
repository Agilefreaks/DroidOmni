package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.InstrumentationTestCaseBase;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ClippingsTest extends InstrumentationTestCaseBase {
  @Inject public Clippings subject;

  public void setUp() throws Exception {
    super.setUp();

    inject(this);
  }

  public void testLastWillReturnAnObservable() throws Exception {
    assertThat(subject.last(), instanceOf(Observable.class));
  }

  public void testCreateWillReturnAnObservable() throws Exception {
    assertThat(subject.create(new ClippingDto()), instanceOf(Observable.class));
  }
}
