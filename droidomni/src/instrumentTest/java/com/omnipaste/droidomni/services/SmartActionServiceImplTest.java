package com.omnipaste.droidomni.services;

import android.content.Context;
import android.content.Intent;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.services.smartaction.SmartAction;
import com.omnipaste.omnicommon.dto.ClippingDto;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SmartActionServiceImplTest extends InstrumentationTestCase {
  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
  }

  public void testRunWillCallStartActivity() throws Exception {
    Context context = mock(Context.class);
    ClippingDto clippingDto = new ClippingDto().setType(ClippingDto.ClippingType.phoneNumber);
    SmartAction smartAction = mock(SmartAction.class);
    Intent intent = new Intent();

    SmartActionServiceImpl smartActionService = new SmartActionServiceImpl(context);
    smartActionService.smartActions.put(ClippingDto.ClippingType.phoneNumber, smartAction);
    when(smartAction.buildIntent(clippingDto)).thenReturn(intent);

    smartActionService.run(clippingDto);

    verify(context, times(1)).startActivity(intent);
  }
}
