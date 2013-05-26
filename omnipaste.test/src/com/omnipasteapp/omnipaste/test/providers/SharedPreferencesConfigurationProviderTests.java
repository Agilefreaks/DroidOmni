package com.omnipasteapp.omnipaste.test.providers;

import android.content.Context;
import android.content.SharedPreferences;
import com.omnipasteapp.omnipaste.providers.SharedPreferencesConfigurationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class SharedPreferencesConfigurationProviderTests {
    private Context mockContext;
    private SharedPreferences mockSharedPreferences;
    private SharedPreferencesConfigurationProvider subject;

    @Before
    public void Setup(){
        mockContext = mock(Context.class);
        mockSharedPreferences = mock(SharedPreferences.class);
        when(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);
        subject = new SharedPreferencesConfigurationProvider();
        subject.setContext(mockContext);
    }

    @Test
    public void getValueGetsSharedPreferencesInPrivateMode(){
        subject.getValue("key");

        verify(mockContext).getSharedPreferences(anyString(), eq(Context.MODE_PRIVATE));
    }

    @Test
    public void getValueCallsSharedPreferencesGetStringValue(){
        subject.getValue("key");

        verify(mockSharedPreferences).getString(eq("key"), anyString());
    }

    @Test
    public void setValueCallsSharedPreferencesEdit(){
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        subject.setValue("key", "value");

        verify(mockSharedPreferences).edit();
    }

    @Test
    public void setValueCallsEditorPutString(){
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        subject.setValue("key", "value");

        verify(mockEditor).putString(eq("key"), eq("value"));
    }

    @Test
    public void setValueCallsCommit(){
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        subject.setValue("key", "value");

        verify(mockEditor).commit();
    }
}
