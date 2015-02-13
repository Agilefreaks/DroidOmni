package com.omnipaste.omniapi.dto.resource.v1;

import com.omnipaste.omniapi.dto.ObjectGraphTest;
import com.omnipaste.omniapi.resource.v1.Clippings;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ClippingsTest extends ObjectGraphTest {
  @Inject public Clippings subject;

  @Override
  protected void setUp() {
  }

  @Test
  public void createWillReturnAnObservable() throws Exception {
    assertThat(subject.create(new ClippingDto()), instanceOf(Observable.class));
  }
}
