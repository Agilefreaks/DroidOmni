package com.omnipaste.phoneprovider.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.phoneprovider.interaction.ActivelyUpdateContact;
import com.omnipaste.phoneprovider.interaction.CreateSmsMessage;

import java.util.Random;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SmsMessageListenerTest extends InstrumentationTestCase {
  private SmsMessageListener smsMessageListener;
  private Context mockContext;
  private ActivelyUpdateContact mockActivelyUpdateContact;
  private SmsMessages mockSmsMessages;
  private CreateSmsMessage mockCreateSmsMessage;
  private Intent intent;

  public void setUp() throws Exception {
    super.setUp();

    mockContext = mock(Context.class);
    mockActivelyUpdateContact = mock(ActivelyUpdateContact.class);
    mockSmsMessages = mock(SmsMessages.class);
    mockCreateSmsMessage = mock(CreateSmsMessage.class);

    smsMessageListener = new SmsMessageListener(
      mockSmsMessages,
      mockCreateSmsMessage,
      mockActivelyUpdateContact);
  }

  public void testOnReceiveWhenExtrasIsNotNullWillCreateSmsMessageWithExtras() {
    contextSmsMessage("123");

    smsMessageListener.onReceive(mockContext, intent);

    verify(mockCreateSmsMessage).with(intent.getExtras());
  }

  public void testOnReceiveWhenExtrasIsNotNullWillCallContactRepositoryFindByNumber() {
    String phoneNumber = String.valueOf(new Random().nextLong());
    contextSmsMessage(phoneNumber);
    when(mockCreateSmsMessage.with(intent.getExtras())).thenReturn(new SmsMessageDto().setPhoneNumber(phoneNumber));

    smsMessageListener.onReceive(mockContext, intent);

    verify(mockActivelyUpdateContact).fromPhoneNumber(phoneNumber);
  }

  public void testOnReceiveWhenExtrasIsNotNullWillPostMessage() {
    SmsMessageDto messageDto = contextSmsMessage("123");

    smsMessageListener.onReceive(mockContext, intent);

    verify(mockSmsMessages).post(messageDto);
  }

  public void testOnReceiveWhenExtrasIsNullWillNotCreateSmsMessageWithExtras() {
    smsMessageListener.onReceive(mockContext, new Intent());

    verify(mockCreateSmsMessage, never()).with(null);
  }

  private Intent contextExtrasNotNull() {
    intent = mock(Intent.class);
    Bundle extras = new Bundle();

    when(intent.getExtras()).thenReturn(extras);

    return intent;
  }

  private SmsMessageDto contextSmsMessage(String phoneNumber) {
    Intent intent = contextExtrasNotNull();

    SmsMessageDto smsMessageDto = new SmsMessageDto().setPhoneNumber(phoneNumber);
    when(mockCreateSmsMessage.with(intent.getExtras())).thenReturn(smsMessageDto);
    when(mockActivelyUpdateContact.fromPhoneNumber(any(String.class))).thenReturn(new ContactDto());
    when(mockSmsMessages.post(any(SmsMessageDto.class))).thenReturn(Observable.<SmsMessageDto>empty());

    return smsMessageDto;
  }
}