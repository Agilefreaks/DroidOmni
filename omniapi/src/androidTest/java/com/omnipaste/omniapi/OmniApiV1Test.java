package com.omnipaste.omniapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

public class OmniApiV1Test extends OmniApiTestCase {

  public void testDevicesWithNoAccessTokenThrowsIllegalArgumentException() throws Exception {
    configuration.setAccessToken(null);
    Boolean throwsException = false;
    try {
      omniApiV1.devices();
    } catch (IllegalArgumentException ex) {
      throwsException = true;
    }

    assertTrue(throwsException);
  }

  public void testDevicesWillReturnTheSameInstance() throws Exception {
    assertThat(omniApiV1.devices(), sameInstance(omniApiV1.devices()));
  }
}
