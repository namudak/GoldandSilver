package com.sb.goldandsilver.database;

import android.app.DialogFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sb.goldandsilver.R;
import com.sb.goldandsilver.provider.GsContract;
import com.sb.goldandsilver.provider.GsOpenHelper;
import com.sb.goldandsilver.provider.GsUrlHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AsyncTaskFragment extends DialogFragment {
    private static String TAG= AsyncTaskFragment.class.getSimpleName();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private static GsUrlHelper mUrlHelper;
    private static GsOpenHelper mOpenHelper;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private static final String URL_START_DATE= "1968-01-02";

    private List mGsList, mGsDiffList;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_async_task);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBarTextView = (TextView) findViewById(R.id.progressbar_test_view);

        this.mContext = this.getApplicationContext();

        mUrlHelper = GsUrlHelper.getInstance(mContext);
        mOpenHelper = GsOpenHelper.getInstance(mContext);

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

        // Just one for flag image
        //new AddFlagTask().execute();

        // Return result
        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        bundle.putSerializable("goldsilver", (Serializable) mGsList);
        bundle.putDouble("currency", rate);
        bundle.putStringArray("diffvalue", diffStr);

        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);

        finish();

    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asynctask, container, false);
        setCancelable(false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView = (TextView) view.findViewById(R.id.progressbar_text_view);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));

        mUrlHelper = GsUrlHelper.getInstance(getActivity());
        mOpenHelper = GsOpenHelper.getInstance(getActivity());

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
//        mGsList = new ArrayList<>();
//        try {
//            mGsList = new RetrieveOpenTask().execute("2015").get();
//        } catch (ExecutionException | InterruptedException ie) {
//            ie.printStackTrace();
//        }

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

        // Just one for flag image
        //new AddFlagTask().execute();

        return view;
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

            GoldSilverUrl gsDb= new GoldSilverUrl(getActivity(), mUrlHelper);

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

            Uri uri = GsContract.CONTENT_URI;
            String[] projection= new String[] {
                    GsContract.Columns.TIME,
                    GsContract.Columns.GOLD_AM_US,
                    GsContract.Columns.SILVER_US
            };
            String selection =
                    GsContract.Columns.TIME + " like ? ";
            String[] selectionArgs = new String[]{
                    "%"+ params[0]+ "%"
            };
            String sortOrder= GsContract.Columns.TIME+ " DESC";

            Cursor cursor= getActivity().getContentResolver().query(
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
                    GsContract.Columns.TIME));
            goldValue[0]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GsContract.Columns.GOLD_AM_US));
            silverValue[0]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GsContract.Columns.SILVER_US));

            cursor.moveToNext();//previous date
            strArray[1]= cursor.getString(cursor.getColumnIndexOrThrow(
                    GsContract.Columns.TIME));
            goldValue[1]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GsContract.Columns.GOLD_AM_US));
            silverValue[1]= cursor.getDouble(cursor.getColumnIndexOrThrow(
                    GsContract.Columns.SILVER_US));

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
     * AsyncTask to add flag to each country
     *
     */
/*
    private class AddFlagTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected Void doInBackground(Void... params) {//1st parameter

            SQLiteDatabase db = mOpenHelper.getWritableDatabase();

            // get name(*) from R.raw.*
            Field[] fields = R.raw.class.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();

                Object theObj= new R.raw();

                Object value= null;
                try {
                    value = fields[i].get(theObj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                InputStream stream = getResources().openRawResource((int)value);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
                Bitmap bmap = BitmapFactory.decodeStream(bufferedInputStream);

                try {
                    ContentValues cv = new ContentValues();
                    cv.put("flag", BitmapUtility.getBytes(bmap));
                    db.update(
                            "CurrencyCountry",              // table name
                            cv,                             // content values
                            " id= ? ",                      // where
                            new String[]{name.toUpperCase()}// ? argument
                    );
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            db.close();

            return null;

        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(Void result) {//3rd parameter

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }
*/

    /**
     * Get latest date plus one from table
     *
     */
    private String getLatestDate() {

        Uri uri = GsContract.CONTENT_URI;
        String[] projection= new String[] {
                GsContract.Columns.TIME,
                GsContract.Columns.GOLD_PM_US
        };
        String selection =
                GsContract.Columns.TIME;

        String sortOrder= GsContract.Columns.TIME+ " DESC";

        Cursor cursor= getActivity().getContentResolver().query(
                uri,
                projection,
                selection,
                null,
                sortOrder
        );

        cursor.moveToNext();

        String strDate= cursor.getString(cursor.getColumnIndexOrThrow(
                GsContract.Columns.TIME));
        String strGoldPmUs= cursor.getString(cursor.getColumnIndexOrThrow(
                GsContract.Columns.GOLD_PM_US));

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
                    GsContract.Columns.TIME + " = ? ";
            String[] selectionArgs = new String[]{
                    strDate
            };
            int rowNum= getActivity().getContentResolver().delete(
                    uri,
                    selection,
                    selectionArgs
            );
            return  strDate;
        }
    }

}
