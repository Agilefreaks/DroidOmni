package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

import junit.framework.TestCase;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class DevicesTest extends TestCase {
  private Devices subject;

  public void setUp() throws Exception {
    super.setUp();

    subject = new Devices(new AccessTokenDto("access"), "http://test.omnipasteapp.com/api");
  }

  public void testCreate() throws Exception {
    assertThat(subject.create("42"), instanceOf(Observable.class));
    assertThat(subject.create("42", "Friendly name"), instanceOf(Observable.class));
  }

  public void testActivate() throws Exception {
    assertThat(subject.activate("42", "42"), instanceOf(Observable.class));
  }
}
