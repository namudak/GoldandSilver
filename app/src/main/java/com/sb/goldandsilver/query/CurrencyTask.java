package com.sb.goldandsilver.query;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.sb.goldandsilver.mvccurrency.CurrencyItem;
import com.sb.goldandsilver.provider.GsOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-11-07.
 */
public class CurrencyTask extends AsyncTask<Void, Void, List>{
    private static final String TAG = CurrencyTask.class.getSimpleName();

    private Context mContext;

    private GsOpenHelper mOpenHelper;

    private List mCurrencyList = new ArrayList<>();

    public CurrencyTask(Context context){
        mContext= context;
        mOpenHelper= GsOpenHelper.getInstance(mContext);
    }

    @Override
    protected void onPreExecute() {//UI

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        try {
            Cursor cursor= db.rawQuery("select flag, currencyId, name, alpha3, id from CurrencyCountry", null);
            byte[] flagBytes= new byte[]{};
            String[] val= new String[4];
            while(cursor.moveToNext()){
                flagBytes= cursor.getBlob(cursor.getColumnIndexOrThrow("flag"));
                val[0]= cursor.getString(cursor.getColumnIndexOrThrow("currencyId"));
                val[1]= cursor.getString(cursor.getColumnIndexOrThrow("name"));
                val[2]= cursor.getString(cursor.getColumnIndexOrThrow("alpha3"));
                val[3]= cursor.getString(cursor.getColumnIndexOrThrow("id"));

                mCurrencyList.add(new CurrencyItem(flagBytes,
                        val[0], val[1], val[2], val[3]));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        db.close();
    }
    @Override
    protected List doInBackground(Void... params) {

        return mCurrencyList;
    }
}
