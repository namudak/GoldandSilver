package com.sb.goldandsilver.query;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.sb.goldandsilver.mvctext.GsTextItem;
import com.sb.goldandsilver.provider.GsContract;
import com.sb.goldandsilver.provider.GsOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 * AsyncTask to retrieve rows from table on conditions
 * if no db then copy carrying db to apk databases folder
 *
 */
class GsTextTask extends AsyncTask<Void, Void, List> {

    private static final String TAG = GsTextTask.class.getSimpleName();

    private Context mContext;

    private final GsOpenHelper mDbHelper;

    private List goldsilverList = new ArrayList<>();
    private String parm;

    public GsTextTask(Context context, String parm){
        mContext= context;
        this.parm= parm;

        mDbHelper = GsOpenHelper.getInstance(mContext);
    }

    @Override
    protected void onPreExecute() {//UI

        try {

            Uri uri = GsContract.CONTENT_URI;
            String[] projection= new String[] {
                    GsContract.Columns.TIME,
                    GsContract.Columns.GOLD_AM_US,
                    GsContract.Columns.GOLD_PM_US,
                    GsContract.Columns.SILVER_US
            };
            String selection =
                    GsContract.Columns.TIME + " like ? ";
            String[] selectionArgs = new String[]{
                    "%"+ parm+ "%"
            };
            String sortOrder= GsContract.Columns.TIME+ " ASC";

            Cursor cursor= mContext.getContentResolver().query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            );

            while(cursor.moveToNext()) {
                String s1= cursor.getString(cursor.getColumnIndexOrThrow(
                        GsContract.Columns.TIME));
                String s2= cursor.getString(cursor.getColumnIndexOrThrow(
                        GsContract.Columns.GOLD_AM_US));
                String s3= cursor.getString(cursor.getColumnIndexOrThrow(
                        GsContract.Columns.GOLD_PM_US));
                String s4= cursor.getString(cursor.getColumnIndexOrThrow(
                        GsContract.Columns.SILVER_US));
                goldsilverList.add(new GsTextItem(s1, s2, s3, s4));
            }

            cursor.close();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }
    @Override
    protected List doInBackground(Void... params) {//1st parameter

        return goldsilverList;

    }
    @Override
    protected void onProgressUpdate(Void...values) {//2nd parameter

    }
    @Override
    protected void onPostExecute(List list) {//3rd parameter

    }


}

