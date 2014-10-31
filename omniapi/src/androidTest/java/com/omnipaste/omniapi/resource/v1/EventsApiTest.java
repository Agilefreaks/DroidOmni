package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.InstrumentationTestCaseBase;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class EventsApiTest extends InstrumentationTestCaseBase {
  @Inject public Events subject;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    inject(this);
  }

  public void testCreateWillReturnAnObservable() throws Exception {
    assertThat(subject.create(new TelephonyEventDto()), instanceOf(Observable.class));
  }
}