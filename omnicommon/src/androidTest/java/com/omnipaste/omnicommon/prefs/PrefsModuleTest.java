package com.omnipaste.omnicommon.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrefsModuleTest extends InstrumentationTestCase {
  private PrefsModule subject;

  public void setUp() throws Exception {
    super.setUp();

    subject = new PrefsModule();
  }

  public void testProvideSharedPreferences() {
    SharedPreferences sharePreferences = mock(SharedPreferences.class);
    Context mockContext = mock(MockContext.class);
    when(mockContext.getSharedPreferences("com.omnipaste.droidomni.debug", Context.MODE_PRIVATE)).thenReturn(sharePreferences);

    assertThat(subject.provideSharedPreferences(mockContext), is(sharePreferences));
  }
}