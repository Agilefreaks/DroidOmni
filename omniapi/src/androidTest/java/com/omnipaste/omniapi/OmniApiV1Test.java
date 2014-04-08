package com.omnipaste.omniapi;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

public class OmniApiV1Test extends TestCase {
  private OmniApiV1 subject;

  public void setUp() throws Exception {
    super.setUp();

    subject = new OmniApiV1("http://test.omnipasteapp.com/api");
  }

  public void testDevicesWithNoAccessTokenThrowsIllegalArgumentException() throws Exception {
    Boolean throwsException = false;
    try {
      subject.devices();
    } catch (IllegalArgumentException ex) {
      throwsException = true;
    }

    assertTrue(throwsException);
  }

  public void testDevicesWillReturnTheSameInstance() throws Exception {
    subject.setAccessToken(new AccessTokenDto());
    assertThat(subject.devices(), sameInstance(subject.devices()));
  }
}
