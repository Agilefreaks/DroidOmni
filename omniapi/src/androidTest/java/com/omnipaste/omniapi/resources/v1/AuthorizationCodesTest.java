package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omniapi.OmniApiV1;

import junit.framework.TestCase;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class AuthorizationCodesTest extends TestCase {
  private AuthorizationCodes authorizationCodes;

  public void setUp() throws Exception {
    super.setUp();

    OmniApiV1 omniApiV1 = new OmniApiV1("client id", "http://test.omnipasteapp.com/api");
    authorizationCodes = omniApiV1.authorizationCodes();
  }

  public void testGetWillReturnAnObservable() throws Exception {
    assertThat(authorizationCodes.get("42", new String[] { "email@email.com" }), instanceOf(Observable.class));
  }
}