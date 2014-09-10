package com.omnipaste.droidomni.services;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.services.smartaction.SmartAction;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SmartActionServiceImplTest extends InstrumentationTestCase {
  private SmartActionServiceImpl smartActionService;

  @Mock
  public Context context;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);

    smartActionService = new SmartActionServiceImpl(context);
  }

  public void testRunWillCallStartActivity() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setType(ClippingDto.ClippingType.PHONE_NUMBER);
    SmartAction smartAction = mock(SmartAction.class);
    Intent intent = new Intent();

    SmartActionService.SMART_ACTIONS.put(ClippingDto.ClippingType.PHONE_NUMBER, smartAction);
    when(smartAction.buildIntent(clippingDto)).thenReturn(intent);

    smartActionService.run(clippingDto);

    verify(context, times(1)).startActivity(intent);
  }

  public void testRunWithNoActivityWillNotCrush() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setType(ClippingDto.ClippingType.ADDRESS).setContent("some random address");
    SmartAction smartAction = mock(SmartAction.class);
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("bogus.navigation:q=" + clippingDto.getContent()));

    SmartActionService.SMART_ACTIONS.put(ClippingDto.ClippingType.ADDRESS, smartAction);
    when(smartAction.buildIntent(clippingDto)).thenReturn(intent);
    Mockito.doThrow(new ActivityNotFoundException()).when(context).startActivity(intent);

    try {
      smartActionService.run(clippingDto);
    }
    catch (Exception exception) {
      if (exception instanceof ActivityNotFoundException) {
        throw exception;
      }
    }
  }
}
