package com.omnipaste.omniapi.dto.resource.v1.user;

import com.omnipaste.omniapi.dto.ObjectGraphTest;
import com.omnipaste.omniapi.resource.v1.user.Devices;

import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class DevicesTest extends ObjectGraphTest {
  @Inject public Devices subject;

  @Override
  public void setUp() {
  }

  @Test
  public void createReturnsAnObservable() throws Exception {
    assertThat(subject.create("42"), instanceOf(Observable.class));
  }

  @Test
  public void activateReturnsAnObservable() throws Exception {
    assertThat(subject.activate("42", "42"), instanceOf(Observable.class));
  }
}
