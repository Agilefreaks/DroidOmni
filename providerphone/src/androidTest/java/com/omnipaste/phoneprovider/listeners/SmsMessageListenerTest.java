package com.omnipaste.phoneprovider.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.phoneprovider.ContactsRepository;
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
  private ContactsRepository mockContactRepository;
  private SmsMessages mockSmsMessages;
  private CreateSmsMessage mockCreateSmsMessage;
  private Contacts mockContacts;
  private Intent intent;

  public void setUp() throws Exception {
    super.setUp();

    mockContext = mock(Context.class);
    mockContactRepository = mock(ContactsRepository.class);
    mockSmsMessages = mock(SmsMessages.class);
    mockCreateSmsMessage = mock(CreateSmsMessage.class);
    mockContacts = mock(Contacts.class);

    smsMessageListener = new SmsMessageListener(
      mockContactRepository,
      mockSmsMessages,
      mockCreateSmsMessage,
      mockContacts);
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

    verify(mockContactRepository).findByPhoneNumber(phoneNumber);
  }

  public void testOnReceiveWhenContactIsNotNullWillGetTheContactFromTheApi() {
    contextContactNotNull("132", 42L);

    smsMessageListener.onReceive(mockContext, intent);

    verify(mockContacts).get(42L);
  }

  public void testOnReceiveWhenContactIsNullWillNotCallGet() {
    contextSmsMessage("123");

    smsMessageListener.onReceive(mockContext, intent);

    verify(mockContacts, never()).get(any(Long.class));
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
    when(mockSmsMessages.post(any(SmsMessageDto.class))).thenReturn(Observable.<SmsMessageDto>empty());

    return smsMessageDto;
  }

  private ContactDto contextContactNotNull(String phoneNumber, Long contactId) {
    contextSmsMessage(phoneNumber);

    ContactDto contactDto = new ContactDto().setContactId(contactId);
    when(mockContactRepository.findByPhoneNumber(phoneNumber)).thenReturn(contactDto);
    when(mockContacts.get(any(Long.class))).thenReturn(Observable.<ContactDto>empty());

    return contactDto;
  }
}