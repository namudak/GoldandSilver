package com.sb.goldandsilver.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class GSUrlHelper extends SQLiteOpenHelper {

	private static GSUrlHelper sInstance;

	public GSUrlHelper(Context context) {
		super(context, GSContract.DB_NAME, null, GSContract.DB_VERSION);
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
						GSContract.TABLE,
						GSContract.Columns.TIME,
						GSContract.Columns.GOLD_AM_US,
						GSContract.Columns.GOLD_PM_US,
						GSContract.Columns.GOLD_AM_GB,
						GSContract.Columns.GOLD_PM_GB,
						GSContract.Columns.GOLD_AM_EU,
						GSContract.Columns.GOLD_PM_EU,
						GSContract.Columns.SILVER_US,
						GSContract.Columns.SILVER_GB,
						GSContract.Columns.SILVER_EU,
						GSContract.Columns.GSRATIO
						);

		Log.d("GSDBHelper","Query to form table: "+sqlQuery);
		sqlDB.execSQL(sqlQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
		sqlDB.execSQL("DROP TABLE IF EXISTS "+ GSContract.TABLE);
		onCreate(sqlDB);
	}

	public static synchronized GSUrlHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new GSUrlHelper(context);
        }
        return sInstance;
    }
}


