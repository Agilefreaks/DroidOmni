package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.InstrumentationTestCaseBase;

import javax.inject.Inject;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class AuthorizationCodesTest extends InstrumentationTestCaseBase {
  @Inject public AuthorizationCodes subject;

  public void setUp() throws Exception {
    super.setUp();

    inject(this);
  }

  public void testGetWillReturnAnObservable() throws Exception {
    assertThat(subject.get("42", new String[] { "email@email.com" }), instanceOf(Observable.class));
  }
}