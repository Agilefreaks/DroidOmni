package com.omnipasteapp.omnicommon.dataaccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.omnipasteapp.omnicommon.domain.Clipping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class ClippingRepository implements IClippingRepository {
  public static final String TABLE_NAME = "clippings";
  public static final String ID_KEY = "id";
  public static final String CONTENT_KEY = "content";
  public static final String DATE_CREATED_KEY = "dateCreated";

  private final OmnipasteDatabase db;

  public ClippingRepository(OmnipasteDatabase db) {
    this.db = db;
  }

  @Override
  public List<Clipping> getForLast24Hours() {
    List<Clipping> clippingList = new ArrayList<Clipping>();
    String selectQuery = "SELECT  * FROM " + TABLE_NAME;

    SQLiteDatabase rb = db.getWritableDatabase();
    Cursor cursor = rb.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
      do {
        Clipping contact = new Clipping();
        contact.setContent(cursor.getString(1));
        contact.setDateCreated(new Date(cursor.getLong(2)));

        clippingList.add(contact);
      } while (cursor.moveToNext());
    }

    return clippingList;
  }

  @Override
  public void save(Clipping clipping) {
    Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    clipping.setDateCreated(cal.getTime());

    SQLiteDatabase wb = db.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(CONTENT_KEY, clipping.getContent());
    values.put(DATE_CREATED_KEY, String.valueOf(clipping.getDateCreated().getTime()));

    wb.insert(TABLE_NAME, null, values);
    wb.close();
  }
}
