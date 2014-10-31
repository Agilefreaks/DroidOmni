package com.omnipaste.omniapi;

import android.test.InstrumentationTestCase;

import java.util.List;

import dagger.ObjectGraph;

public abstract class InstrumentationTestCaseBase extends InstrumentationTestCase {

  private ObjectGraph objectGraph;

  public void setUp() throws Exception {
    super.setUp();

    objectGraph = ObjectGraph.create(new OmniApiTestModule(getInstrumentation().getContext()));
  }

  public ObjectGraph plus(List<Object> modules) {
    if (modules == null) {
      throw new IllegalArgumentException(
          "You can't plus a null module, review your getModules() implementation");
    }
    return objectGraph.plus(modules.toArray());
  }

  protected void inject(Object object) {
    objectGraph.inject(object);
  }
}
