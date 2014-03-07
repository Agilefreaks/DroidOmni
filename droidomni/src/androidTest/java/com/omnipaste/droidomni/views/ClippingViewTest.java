package com.omnipaste.droidomni.views;

import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.services.SmartActionService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClippingViewTest extends InstrumentationTestCase {
  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
  }

  public void testSmartActionClickedWillCallRunOnSmartActionService() throws Exception {
    SmartActionService smartActionService = mock(SmartActionService.class);
    ClippingDto clippingDto = new ClippingDto();

    ClippingView_ clippingView = new ClippingView_(getInstrumentation().getContext());
    clippingView.smartActionService = smartActionService;
    clippingView.clippingDto = clippingDto;

    clippingView.smartActionButtonClicked();

    verify(smartActionService, times(1)).run(clippingDto);
  }
}
