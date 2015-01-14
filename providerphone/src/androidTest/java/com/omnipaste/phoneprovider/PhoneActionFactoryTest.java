package com.omnipaste.phoneprovider;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.test.InstrumentationTestCase;

import com.omnipaste.phoneprovider.actions.PhoneCallInitiate;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class PhoneActionFactoryTest extends InstrumentationTestCase {
  private PhoneActionFactory actionPhoneActionFactory;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    actionPhoneActionFactory = new PhoneActionFactory(mock(Context.class), mock(TelephonyManager.class));
  }

  public void testCreateWithCallWillReturnActionCall() throws Exception {
    assertThat(actionPhoneActionFactory.create(PhoneCallState.INITIATE), instanceOf(PhoneCallInitiate.class));
  }
}