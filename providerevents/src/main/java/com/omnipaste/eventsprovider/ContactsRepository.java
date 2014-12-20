package com.omnipaste.eventsprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.omnipaste.omnicommon.dto.ContactDto;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactsRepository {
  private Context context;

  @Inject
  public ContactsRepository(Context context) {
    this.context = context;
  }

  public String findByPhoneNumber(String phoneNumber) {
    ContentResolver resolver = context.getContentResolver();
    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
    Cursor cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

    if (cursor == null) {
      return null;
    }

    String contactName = null;
    if (cursor.moveToFirst()) {
      contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
    }

    if (!cursor.isClosed()) {
      cursor.close();
    }

    return contactName;
  }

  public List<ContactDto> findAll() {
    return Arrays.asList(new ContactDto(), new ContactDto());
  }
}
