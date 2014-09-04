package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omniapi.OmniApiTestCase;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class EventsApiTest extends OmniApiTestCase {
  private Events events;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    events = omniApiV1.events();
  }

  public void testCreateWillReturnAnObservable() throws Exception {
    assertThat(events.create(new TelephonyEventDto()), instanceOf(Observable.class));
  }
}