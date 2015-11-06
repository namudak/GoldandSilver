package com.sb.goldandsilver.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class GsUrlHelper extends SQLiteOpenHelper {

	private static GsUrlHelper sInstance;

	public GsUrlHelper(Context context) {
		super(context, GsContract.DB_NAME, null, GsContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDB) {
		String sqlQuery =
				String.format("CREATE TABLE %s (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
						"%s TEXT UNIQUE, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT"+ ")",
						GsContract.TABLE,
						GsContract.Columns.TIME,
						GsContract.Columns.GOLD_AM_US,
						GsContract.Columns.GOLD_PM_US,
						GsContract.Columns.GOLD_AM_GB,
						GsContract.Columns.GOLD_PM_GB,
						GsContract.Columns.GOLD_AM_EU,
						GsContract.Columns.GOLD_PM_EU,
						GsContract.Columns.SILVER_US,
						GsContract.Columns.SILVER_GB,
						GsContract.Columns.SILVER_EU,
						GsContract.Columns.GSRATIO
						);

		Log.d("GSDBHelper","Query to form table: "+sqlQuery);
		sqlDB.execSQL(sqlQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
		sqlDB.execSQL("DROP TABLE IF EXISTS "+ GsContract.TABLE);
		onCreate(sqlDB);
	}

	public static synchronized GsUrlHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new GsUrlHelper(context);
        }
        return sInstance;
    }
}


