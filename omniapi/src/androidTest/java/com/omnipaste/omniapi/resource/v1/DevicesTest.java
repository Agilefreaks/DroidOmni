package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.InstrumentationTestCaseBase;

import javax.inject.Inject;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class DevicesTest extends InstrumentationTestCaseBase {
  @Inject public Devices subject;

  public void setUp() throws Exception {
    super.setUp();

    inject(this);
  }

  public void testCreate() throws Exception {
    assertThat(subject.create("42"), instanceOf(Observable.class));
    assertThat(subject.create("42", "Friendly name"), instanceOf(Observable.class));
  }

  public void testActivate() throws Exception {
    assertThat(subject.activate("42", "42"), instanceOf(Observable.class));
  }
}
