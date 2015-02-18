package com.omnipaste.phoneprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;

import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.NumberDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

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

  public Observable<ContactDto> find(final int skip) {
    return Observable.create(new Observable.OnSubscribe<ContactDto>() {
      @Override
      public void call(Subscriber<? super ContactDto> subscriber) {
        ContentResolver resolver = context.getContentResolver();
        String[] dataProjection = new String[]{
          ContactsContract.CommonDataKinds.StructuredName.PHOTO_THUMBNAIL_URI,
          ContactsContract.CommonDataKinds.StructuredName.HAS_PHONE_NUMBER,
          ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID,
          ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
          ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
          ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
          ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
        };

        String where = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.HAS_PHONE_NUMBER + " = ?";
        String[] whereParameters = new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, "1"};
        Cursor data = resolver.query(ContactsContract.Data.CONTENT_URI, dataProjection, where, whereParameters, null);

        if (data != null && data.moveToPosition(skip)) {
          final int rawContactIdIndex = data.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID);

          do {
            ContactDto contact = new ContactDto(data.getLong(rawContactIdIndex));

            fetchContactName(data, contact);

            if (contact.getName() != null || contact.getFirstName() != null) {
              fetchPhoto(data, contact);
              fetchPhone(resolver, contact);
              subscriber.onNext(contact);
            }
          } while (data.moveToNext());

          if (!data.isClosed()) {
            data.close();
          }
        }

        subscriber.onCompleted();
      }
    });
  }

  private void fetchPhoto(Cursor data, ContactDto contact) {
    final int indexPhotoThumbnailUri = data.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHOTO_THUMBNAIL_URI);

    String photoThumbnailUri = data.getString(indexPhotoThumbnailUri);

    if (photoThumbnailUri != null && !photoThumbnailUri.isEmpty()) {
      try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(photoThumbnailUri));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        contact.setImage(Base64.encodeToString(byteArray, Base64.DEFAULT));
      } catch (IOException ignore) {
      }
    }
  }

  private void fetchPhone(ContentResolver resolver, ContactDto contact) {
    final String[] phoneProjection = new String[]{
      ContactsContract.CommonDataKinds.Phone.NUMBER,
      ContactsContract.CommonDataKinds.Phone.TYPE
    };

    String where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
    String[] whereParameters = new String[]{contact.getContactId().toString(), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
    Cursor phone = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection, where, whereParameters, null);

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

  private void fetchContactName(Cursor cursor, ContactDto contact) {
    final int indexDisplayName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);
    final int indexGivenName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
    final int indexFamilyName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
    final int indexMiddleName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME);

    contact
      .setName(cursor.getString(indexDisplayName))
      .setLastName(cursor.getString(indexFamilyName))
      .setFirstName(cursor.getString(indexGivenName))
      .setMiddleName(cursor.getString(indexMiddleName));
  }
}
