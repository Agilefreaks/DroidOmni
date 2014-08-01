package com.omnipaste.phoneprovider;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.domain.PhoneAction;
import com.omnipaste.phoneprovider.actions.Call;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class ActionFactoryTest extends InstrumentationTestCase {
  private ActionFactory actionFactory;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    actionFactory = new ActionFactory(mock(Context.class));
  }

  public void testCreateWithCallWillReturnActionCall() throws Exception {
    assertThat(actionFactory.create(PhoneAction.CALL), instanceOf(Call.class));
  }
}