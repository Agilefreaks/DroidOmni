package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omniapi.OmniApiTestCase;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class AuthorizationCodesTest extends OmniApiTestCase {
  private AuthorizationCodes authorizationCodes;

  public void setUp() throws Exception {
    super.setUp();

    authorizationCodes = omniApiV1.authorizationCodes();
  }

  public void testGetWillReturnAnObservable() throws Exception {
    assertThat(authorizationCodes.get("42", new String[] { "email@email.com" }), instanceOf(Observable.class));
  }
}