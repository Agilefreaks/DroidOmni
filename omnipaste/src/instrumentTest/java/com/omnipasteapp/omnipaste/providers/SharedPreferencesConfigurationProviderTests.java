package com.omnipasteapp.omnipaste.providers;

import android.content.Context;
import android.content.SharedPreferences;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("MagicConstant")
public class SharedPreferencesConfigurationProviderTests extends TestCase {
  @Mock
  private Context mockContext;
  @Mock
  private SharedPreferences mockSharedPreferences;

  private SharedPreferencesConfigurationProvider subject;

  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(mockContext.getSharedPreferences(anyString(), eq(Context.MODE_PRIVATE))).thenReturn(mockSharedPreferences);

    subject = new SharedPreferencesConfigurationProvider();
    subject.context = mockContext;
  }

  public void testGetValueGetsSharedPreferencesInPrivateMode() {
    subject.getValue("key");

    verify(mockContext).getSharedPreferences(anyString(), eq(Context.MODE_PRIVATE));
  }

  public void testGetValueCallsSharedPreferencesGetStringValue() {
    subject.getValue("key");

    verify(mockSharedPreferences).getString(eq("key"), anyString());
  }

  public void testSetValueCallsSharedPreferencesEdit() {
    SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
    when(mockSharedPreferences.edit()).thenReturn(mockEditor);

    subject.setValue("key", "value");

    verify(mockSharedPreferences).edit();
  }

  public void testSetValueCallsEditorPutString() {
    SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
    when(mockSharedPreferences.edit()).thenReturn(mockEditor);

    subject.setValue("key", "value");

    verify(mockEditor).putString(eq("key"), eq("value"));
  }

  public void testSetValueCallsCommit() {
    SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
    when(mockSharedPreferences.edit()).thenReturn(mockEditor);

    subject.setValue("key", "value");

    verify(mockEditor).commit();
  }
}
