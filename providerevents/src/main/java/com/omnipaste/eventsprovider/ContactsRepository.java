package com.omnipaste.eventsprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;

import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.NumberDto;

import java.util.ArrayList;
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
    ContentResolver resolver = context.getContentResolver();
    String[] rawContactsProjection = new String[]{ContactsContract.RawContacts._ID};
    Cursor rawContacts = resolver.query(ContactsContract.RawContacts.CONTENT_URI, rawContactsProjection, null, null, null);
    List<ContactDto> contacts = new ArrayList<>();

    if (rawContacts == null) {
      return null;
    }

    if (rawContacts.moveToFirst()) {
      final int idIndex = rawContacts.getColumnIndex(ContactsContract.RawContacts._ID);

      do {
        ContactDto contact = new ContactDto(rawContacts.getLong(idIndex));

        fetchContactName(resolver, contact);
        fetchPhone(resolver, contact);
        fetchPhoto(resolver, contact);

        contacts.add(contact);
      } while (rawContacts.moveToNext());
    }

    if (!rawContacts.isClosed()) {
      rawContacts.close();
    }

    return contacts;
  }

  private void fetchPhoto(ContentResolver resolver, ContactDto contact) {
    final String[] photoProjection = new String[]{
      ContactsContract.CommonDataKinds.Photo.PHOTO
    };

    String[] whereParameters = new String[]{contact.id.toString(), ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE};
    Cursor photo = resolver.query(ContactsContract.Data.CONTENT_URI, photoProjection, where(), whereParameters, null);

    if (photo.moveToFirst()) {
      final int indexPhoto = photo.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO);
      byte[] blob = photo.getBlob(indexPhoto);

      if (blob != null) {
        contact.setPhoto(Base64.encodeToString(blob, Base64.DEFAULT));
      }
    }

    if (!photo.isClosed()) {
      photo.close();
    }
  }

  private void fetchPhone(ContentResolver resolver, ContactDto contact) {
    final String[] phoneProjection = new String[]{
      ContactsContract.CommonDataKinds.Phone.NUMBER,
      ContactsContract.CommonDataKinds.Phone.TYPE
    };

    String[] whereParameters = new String[]{contact.id.toString(), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
    Cursor phone = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection, where(), whereParameters, null);

    if (phone.moveToFirst()) {
      final int indexNumber = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
      final int indexType = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);

      do {
        final String number = phone.getString(indexNumber);
        final int type = phone.getType(indexType);

        CharSequence phoneType = ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(), type, "Custom");
        contact.addNumber(new NumberDto(number, phoneType.toString()));
      } while (phone.moveToNext());
    }

    if (!phone.isClosed()) {
      phone.close();
    }
  }

  private void fetchContactName(ContentResolver resolver, ContactDto contact) {
    final String[] structuredNameProjection = new String[]{
      ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
      ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
      ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
      ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
    };

    String[] whereParameters = new String[]{contact.id.toString(), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
    Cursor structuredName = resolver.query(ContactsContract.Data.CONTENT_URI, structuredNameProjection, where(), whereParameters, null);

    if (structuredName.moveToFirst()) {
      final int indexDisplayName = structuredName.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);
      final int indexGivenName = structuredName.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
      final int indexFamilyName = structuredName.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
      final int indexMiddleName = structuredName.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME);

      contact
        .setDisplayName(structuredName.getString(indexDisplayName))
        .setFamilyName(structuredName.getString(indexFamilyName))
        .setGivenName(structuredName.getString(indexGivenName))
        .setMiddleName(structuredName.getString(indexMiddleName));
    }

    if (!structuredName.isClosed()) {
      structuredName.close();
    }
  }

  private String where() {
    return ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
  }
}
