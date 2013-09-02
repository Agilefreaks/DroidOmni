package com.omnipasteapp.omnicommon.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

public class OmnipasteDatabase extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "omnipaste.db";

  @Inject
  public OmnipasteDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    createClippingsTable(db);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  private void createClippingsTable(SQLiteDatabase db) {
    db.execSQL("create table " + ClippingRepository.TABLE_NAME + " (" +
        ClippingRepository.ID_KEY + " integer primary key autoincrement," +
        ClippingRepository.CONTENT_KEY + " text," +
        ClippingRepository.DATE_CREATED_KEY + " integer)");
  }
}
