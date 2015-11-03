package com.sb.goldandsilver.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sb.goldandsilver.R;
import com.sb.goldandsilver.list.GoldSilverItem;
import com.sb.goldandsilver.provider.GSContract;
import com.sb.goldandsilver.provider.GSOpenHelper;
import com.sb.goldandsilver.provider.GSUrlHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AsyncTaskActivity extends Activity {
    private static String TAG= AsyncTaskActivity.class.getSimpleName();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private static Context mContext;

    private static GSUrlHelper mUrlHelper;
    private static GSOpenHelper mOpenHelper;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private static final String URL_START_DATE= "1968-01-02";

    private List mGsList, mGsDiffList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_async_task);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBarTextView = (TextView) findViewById(R.id.progressbar_test_view);

        this.mContext = this.getApplicationContext();

        mUrlHelper = GSUrlHelper.getInstance(mContext);
        mOpenHelper = GSOpenHelper.getInstance(mContext);

        Calendar cal = Calendar.getInstance();

        String strStart;
        String strToday = dateFormat.format(cal.getTime());

        // if already apk db exist
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (mOpenHelper.checkDataBase(db)) {
            strStart = getLatestDate();
        } else { // if 1st loading
            strStart = URL_START_DATE;
        }

        String[] sDate = new String[]{strStart, strToday};
        // Check if new data at url site, get it and insert into db
        new RetrieveUrlTask().execute(sDate);

        // Check if first run, copy carrying db into apk folder
        mGsList = new ArrayList<>();
        try {
            mGsList = new RetrieveOpenTask().execute("2015").get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        // Get a currency exchange rate usd vs krw
        double rate= 0.0;
        try {
            rate = new RetrieveCurrencyTask().execute("USD_KRW").get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        // Get a difference between a specific date and previous one
        String[] diffStr= null;
        try {
            diffStr = new RetrieveDiffTask().execute("2015").get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        // Return result
        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        bundle.putSerializable("goldsilver", (Serializable) mGsList);
        bundle.putDouble("currency", rate);
        bundle.putStringArray("diffvalue", diffStr);

        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);

        finish();

    }
    /**
     * AsyncTask for retrieving from url and insert into db apk /databases/ folder
     * if no db then create db
     */
    private class RetrieveUrlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected Void doInBackground(String... params) {//1st parameter

            GoldSilverUrl gsDb= new GoldSilverUrl(mContext, mUrlHelper);

            gsDb.RetrieveJsonData(params[0], params[1]);

            return null;
        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(Void result) {//3rd parameter
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }

    /**
     * AsyncTask to retrieve rows from table on conditions
     * if no db then copy carrying db to apk databases folder
     *
     */
    private class RetrieveOpenTask extends AsyncTask<String, Void, List> {

        private List goldsilverList = new ArrayList<>();

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected List doInBackground(String... params) {//1st parameter

            Uri uri = GSContract.CONTENT_URI;
            String[] projection= new String[] {
                    GSContract.Columns.TIME,
                    GSContract.Columns.GOLD_AM_US,
                    GSContract.Columns.SILVER_US
            };
            String selection =
                    GSContract.Columns.TIME + " like ? ";
            String[] selectionArgs = new String[]{
                    "%"+ params[0]+ "%"
            };
            String sortOrder= GSContract.Columns.TIME+ " ASC";

            Cursor cursor= mContext.getContentResolver().query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            );

            while(cursor.moveToNext()) {
                String s1= cursor.getString(cursor.getColumnIndexOrThrow(
                        GSContract.Columns.TIME));
                String s2= cursor.getString(cursor.getColumnIndexOrThrow(
                        GSContract.Columns.GOLD_AM_US));
                String s3= cursor.getString(cursor.getColumnIndexOrThrow(
                        GSContract.Columns.SILVER_US));
                goldsilverList.add(new GoldSilverItem(s1, s2, s3));
            }

            cursor.close();

            return goldsilverList;

        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(List list) {//3rd parameter

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }

    /**
     * AsyncTask to retrieve currency exchange rate
     *
     */
    private class RetrieveCurrencyTask extends AsyncTask<String, Void, Double> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected Double doInBackground(String... params) {//1st parameter

            CurrencyUrl currencyDb= new CurrencyUrl();

            return currencyDb.RetrieveCurrencyData(params[0]);

        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(Double value) {//3rd parameter

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }

    /**
     * AsyncTask to retrieve difference price between a specific date and
     * previous ons for gold and silver respectively
     *
     */
    private class RetrieveDiffTask extends AsyncTask<String, Void, String[]> {


        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected String[] doInBackground(String... params) {//1st parameter

            Uri uri = GSContract.CONTENT_URI;
            String[] projection= new String[] {
                    GSContract.Columns.TIME,
                    GSContract.Columns.GOLD_AM_US,
                    GSContract.Columns.SILVER_US
            };
            String selection =
                    GSContract.Columns.TIME + " like ? ";
            String[] selectionArgs = new String[]{
                    "%"+ params[0]+ "%"
            };
            String sortOrder= GSContract.Columns.TIME+ " DESC";

            Cursor cursor= mContext.getContentResolver().query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            );

            String[] strArray= new String[4];
            double[] goldValue= new double[2];
            double[] silverValue= new double[2];

            cursor.moveToNext();//newest date
            strArray[0]= cursor.getString(cursor.getColumnIndexOrThrow(
                    GSContract.Columns.TIME));
            goldValue[0]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GSContract.Columns.GOLD_AM_US));
            silverValue[0]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GSContract.Columns.SILVER_US));

            cursor.moveToNext();//previous date
            strArray[1]= cursor.getString(cursor.getColumnIndexOrThrow(
                    GSContract.Columns.TIME));
            goldValue[1]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GSContract.Columns.GOLD_AM_US));
            silverValue[1]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GSContract.Columns.SILVER_US));

            //difference value between dates
            strArray[2]= String.format("%.3f", goldValue[0] - goldValue[1]);
            strArray[3]= String.format("%.3f", silverValue[0] - silverValue[1]);

            cursor.close();

            return strArray;

        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(String[] string) {//3rd parameter

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }

    /**
     * Get latest date plus one from table
     *
     */
    private String getLatestDate() {

        Uri uri = GSContract.CONTENT_URI;
        String[] projection= new String[] {
                GSContract.Columns.TIME,
                GSContract.Columns.GOLD_PM_US
        };
        String selection =
                GSContract.Columns.TIME;

        String sortOrder= GSContract.Columns.TIME+ " DESC";

        Cursor cursor= mContext.getContentResolver().query(
                uri,
                projection,
                selection,
                null,
                sortOrder
        );

        cursor.moveToNext();

        String strDate= cursor.getString(cursor.getColumnIndexOrThrow(
                GSContract.Columns.TIME));
        String strGoldPmUs= cursor.getString(cursor.getColumnIndexOrThrow(
                GSContract.Columns.GOLD_PM_US));

        cursor.close();

        if(!strGoldPmUs.equals("null")) {
            // add 1 day on latest date
            String[] strYmd = strDate.split("-");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(strYmd[0]),
                    Integer.parseInt(strYmd[1]) - 1,
                    Integer.parseInt(strYmd[2]));
            cal.add(cal.DATE, 1);

            return dateFormat.format(cal.getTime());
        } else {
            selection =
                    GSContract.Columns.TIME + " = ? ";
            String[] selectionArgs = new String[]{
                    strDate
            };
            int rowNum= mContext.getContentResolver().delete(
                    uri,
                    selection,
                    selectionArgs
            );
            return  strDate;
        }
    }

}
