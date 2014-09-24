package com.group.util;

import android.R.string;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int versionnumber =1;
	private static final String localdatabase="M";
	
	public DatabaseHelper(Context context) {
		super(context, localdatabase,null,versionnumber);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS memberinfo(id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Gname VARCHAR(20)," +
				"g VARCHAR(20)," +
				"member VARCHAR(20))");
		db.execSQL("CREATE TABLE IF NOT EXISTS messageinfo(id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Gname VARCHAR(20)," +
				"g VARCHAR(20)," +
				"date INT ," +
				"message TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("ALTER TABLE user ADD COLUMN other TEXT");
		
	}
}
