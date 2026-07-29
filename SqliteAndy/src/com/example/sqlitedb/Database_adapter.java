package com.example.sqlitedb;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database_adapter {
	protected static final String TAG = "DataAdapter";
	protected final Context mContext;
	protected SQLiteDatabase mDb;
	public DataBaseHelper mDbHelper;

	public final static String LOGTAG = "DBDB";

	public Database_adapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public Database_adapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public Database_adapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public boolean insert_student_data(Student stu_obj) {
		try {
			Log.d(LOGTAG, "" + stu_obj.toString());
			
			ContentValues cv = new ContentValues();
			cv.put("name", stu_obj.getStu_name());
			cv.put("marks", stu_obj.getStu_marks());
			cv.put("stu_img", stu_obj.getStu_img());

			mDb.insert("stu", null, cv);
			Log.d(LOGTAG, "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d(LOGTAG, ex.toString());
			return false;
		}

	}

	public boolean update_student_data(Student stu_obj) {
		try {

			Log.d(LOGTAG, "" + stu_obj.toString());
			ContentValues cv = new ContentValues();
			cv.put("name", stu_obj.getStu_name());
			cv.put("marks", stu_obj.getStu_marks());
			cv.put("stu_img", stu_obj.getStu_img());
			
			mDb.update("stu", cv, "id " + "=" + stu_obj.getStu_id(), null);
			Log.d(LOGTAG, "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d(LOGTAG, ex.toString());
			return false;
		}
	}

	public ArrayList<Student> getAllStudents(String key, String val)

	{
		ArrayList<Student> arr_list;
		arr_list = new ArrayList<Student>();

		String selectQuery = "";
		if (key != null) {
			if (key.equals("detail")) {
				selectQuery = "SELECT  * FROM stu WHERE id = " + val;
			}
		} else {
			selectQuery = "SELECT  * FROM stu";
		}

		Cursor cursor = mDb.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				Student hm = new Student();

				hm.setStu_id(cursor.getInt(0));
				hm.setStu_name((cursor.getString(1)));
				hm.setStu_marks("" + cursor.getInt(2));
				hm.setStu_img(""+cursor.getString(3));

				arr_list.add(hm);

			} while (cursor.moveToNext());
		}

		return arr_list;
	}

	public void delete_row_from_table(String table_name, String clmnm,
			String clmn_val) {

		String qry = "DELETE FROM " + table_name + " WHERE " + clmnm + "='"
				+ clmn_val + "'";
		mDb.execSQL(qry);
	}

	// Rishi Change ends
}
