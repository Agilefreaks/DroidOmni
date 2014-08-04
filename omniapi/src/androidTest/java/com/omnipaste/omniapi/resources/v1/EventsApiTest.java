package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omniapi.OmniApiV1;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import junit.framework.TestCase;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class EventsApiTest extends TestCase {
  private Events events;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    OmniApiV1 omniApiV1 = new OmniApiV1("client id", "http://test.omnipasteapp.com/api");
    omniApiV1.setAccessToken(new AccessTokenDto("access"));
    events = omniApiV1.events();
  }

  public void testCreateWillReturnAnObservable() throws Exception {
    assertThat(events.create(new TelephonyEventDto()), instanceOf(Observable.class));
  }
}