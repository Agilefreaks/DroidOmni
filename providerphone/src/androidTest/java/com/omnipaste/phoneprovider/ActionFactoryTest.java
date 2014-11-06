package com.omnipaste.phoneprovider;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.phoneprovider.actions.Call;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class ActionFactoryTest extends InstrumentationTestCase {
  private ActionFactory actionActionFactory;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    actionActionFactory = new ActionFactory(mock(Context.class), mock(TelephonyManager.class), null);
  }

  public void testCreateWithCallWillReturnActionCall() throws Exception {
    assertThat(actionActionFactory.create(PhoneAction.CALL), instanceOf(Call.class));
  }
}