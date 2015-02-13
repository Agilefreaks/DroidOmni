package com.omnipaste.omniapi.dto;

import android.content.Context;

import org.junit.Before;

import java.util.List;

import dagger.ObjectGraph;

import static org.mockito.Mockito.mock;

public abstract class ObjectGraphTest {
  protected ObjectGraph objectGraph;

  @Before
  public void context() {
    objectGraph = ObjectGraph.create(new OmniApiTestModule(mock(Context.class)));
    setUp();
    inject(this);
  }

  public ObjectGraph plus(List<Object> modules) {
    if (modules == null) {
      throw new IllegalArgumentException(
        "You can't plus a null module, review your getModules() implementation");
    }
    return objectGraph.plus(modules.toArray());
  }

  protected abstract void setUp();

  protected void inject(Object object) {
    objectGraph.inject(object);
  }
}
