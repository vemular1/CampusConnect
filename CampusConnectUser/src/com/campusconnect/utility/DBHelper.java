package com.campusconnect.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	SQLiteDatabase db;

	public DBHelper(Context context) {
		super(context, "Complaints", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table contacts(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "contact_firstname Text,contact_lastname,contact_phone Text,contact_email Text,"
				+ "Time Text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public long insertData(String email, String firstname, String lastname,
			String phone, String time) {
		db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("contact_email", email);
		cv.put("contact_firstname", firstname);
		cv.put("contact_lastname", lastname);
		cv.put("contact_phone", phone);
		cv.put("contact_time", time);

		long row = db.insert("loc", null, cv);
		return row;
	}

	/*public List<String> SelectContacts(String tag, String email) {

		List<String> contacts = new ArrayList<>();

		db = this.getReadableDatabase();
		Cursor c = null;

		int username = c.getColumnIndex("userName");
		int password = c.getColumnIndex("password");

		if (tag.equals("all"))
			c = db.query("contact", null, null, null, null, null, null);
		else if (tag.equals("latest"))
			c = db.query("contact", null, null, null, null, null, null);

		while (c.moveToNext()) {
			String Uid = c.getString(username);
			String Pwd = c.getString(password);
		}


	}*/

	public boolean SelectData(String userName, String Password) {
		boolean flag12 = false;
		db = this.getReadableDatabase();

		Cursor c = db.query("loc", null, null, null, null, null, null);

		int username = c.getColumnIndex("userName");
		int password = c.getColumnIndex("password");

		while (c.moveToNext()) {
			String Uid = c.getString(username);
			String Pwd = c.getString(password);

			if (userName.equals(Uid) && Password.equals(Pwd)) {
				flag12 = true;
				break;
			} else {
				flag12 = false;
			}
		}

		return flag12;
	}

}
