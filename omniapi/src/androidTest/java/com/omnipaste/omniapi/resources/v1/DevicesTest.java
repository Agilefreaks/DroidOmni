package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omniapi.OmniApiV1;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import junit.framework.TestCase;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class DevicesTest extends TestCase {
  private Devices subject;

  public void setUp() throws Exception {
    super.setUp();

    OmniApiV1 omniApiV1 = new OmniApiV1("client id", "http://test.omnipasteapp.com/api");
    omniApiV1.setAccessToken(new AccessTokenDto("access"));
    subject = omniApiV1.devices();
  }

  public void testCreate() throws Exception {
    assertThat(subject.create("42"), instanceOf(Observable.class));
    assertThat(subject.create("42", "Friendly name"), instanceOf(Observable.class));
  }

  public void testActivate() throws Exception {
    assertThat(subject.activate("42", "42"), instanceOf(Observable.class));
  }
}
