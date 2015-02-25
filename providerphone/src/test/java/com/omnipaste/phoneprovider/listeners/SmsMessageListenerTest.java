package com.omnipaste.phoneprovider.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.phoneprovider.ContactsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SmsMessageListenerTest {
  private SmsMessageListener smsMessageListener;

  @Mock Context mockContext;
  @Mock ContactsRepository mockContactRepository;
  @Mock SmsMessages mockSmsMessages;

  @Before
  public void context() throws Exception {
    smsMessageListener = new SmsMessageListener(mockContext, mockContactRepository, mockSmsMessages);
  }

  @Test
  private void onReceiveContextExpectedBehaviour() {
    Intent intent = new Intent();
    Bundle bundle = new Bundle();

    intent.putExtras(bundle);
  }
}