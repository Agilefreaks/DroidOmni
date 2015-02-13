package com.omnipaste.omniapi.dto.resource.v1;

import com.omnipaste.omniapi.dto.ObjectGraphTest;
import com.omnipaste.omniapi.resource.v1.AuthorizationCodes;

import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class AuthorizationCodesTest extends ObjectGraphTest {
  @Inject public AuthorizationCodes subject;

  @Override
  public void setUp() {
  }

  @Test
  public void getWillReturnAnObservable() throws Exception {
    assertThat(subject.get("42", new String[] { "email@email.com" }), instanceOf(Observable.class));
  }
}