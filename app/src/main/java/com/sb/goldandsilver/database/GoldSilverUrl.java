package com.sb.goldandsilver.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.sb.goldandsilver.database.api.ApiUrl;
import com.sb.goldandsilver.provider.GsContract;
import com.sb.goldandsilver.provider.GsUrlHelper;
import com.sb.goldandsilver.utility.network.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by namudak on 2015-09-20.
 */
public class GoldSilverUrl {

    Context mContext= null;
    GsUrlHelper mDbHelper= null;

    public GoldSilverUrl(Context context, GsUrlHelper helper){
        this.mContext= context;
        mDbHelper= helper;
    }

    public void RetrieveJsonData(String start, String today) {

        String strGold= String.format(ApiUrl.URL_METAL_GOLD, start, today);
        String strSilver= String.format(ApiUrl.URL_METAL_SILVER, start, today);

        // Create database
        GsUrlHelper helper = GsUrlHelper.getInstance(mContext);

        NetworkUtility network= new NetworkUtility();

        try {
            String jsonStringGold = network.getResponse(strGold);
            String jsonStringSilver = network.getResponse(strSilver);

            JSONObject jsonObjectGold = new JSONObject(jsonStringGold);
            JSONArray jsonArrayGold = jsonObjectGold.getJSONObject("dataset").getJSONArray("data");
            JSONObject jsonObjectSilver = new JSONObject(jsonStringSilver);
            JSONArray jsonArraySilver = jsonObjectSilver.getJSONObject("dataset").getJSONArray("data");

            for (int i = 0; i < jsonArrayGold.length(); i++) {
                String[] strArrayGold = jsonArrayGold.getString(i).split(",");
                String[] strArraySilver = jsonArraySilver.getString(i).split(",");

                String metalStr= strArrayGold[0].substring(2, 12);
                String tempStr= strArrayGold[6].replace("]", "");
                strArrayGold[6]= tempStr;
                tempStr= strArraySilver[3].replace("]", "");
                strArraySilver[3]= tempStr;
                for(int j= 1; j< 7; j++)
                    metalStr+= ","+ (strArrayGold[j].equals("null") ? null : strArrayGold[j]) ;
                for(int k= 1; k< 4; k++)
                    metalStr+= ","+ (strArraySilver[k].equals("null") ? null : strArraySilver[k]);

                if( strArrayGold[1].endsWith("null") ||
                        strArraySilver[1].equals("null") ) {
                    metalStr += "," + null;
                } else {
                    Float f1 = Float.valueOf(strArrayGold[1]);
                    Float f2 = Float.valueOf(strArraySilver[1]);
                    metalStr += "," + Float.toString(f1/ f2);
                }

                ContentValues values = new ContentValues();

                String[] valueArray= metalStr.split(",");

                values.clear();
                values.put(GsContract.Columns.TIME, valueArray[0]);
                values.put(GsContract.Columns.GOLD_AM_US, valueArray[1]);
                values.put(GsContract.Columns.GOLD_PM_US, valueArray[2]);
                values.put(GsContract.Columns.GOLD_AM_GB, valueArray[3]);
                values.put(GsContract.Columns.GOLD_PM_GB, valueArray[4]);
                values.put(GsContract.Columns.GOLD_AM_EU, valueArray[5]);
                values.put(GsContract.Columns.GOLD_PM_EU, valueArray[6]);
                values.put(GsContract.Columns.SILVER_US, valueArray[7]);
                values.put(GsContract.Columns.SILVER_GB, valueArray[8]);
                values.put(GsContract.Columns.SILVER_EU, valueArray[9]);
                values.put(GsContract.Columns.GSRATIO, valueArray[10]);

                Uri uri = GsContract.CONTENT_URI;
                mContext.getContentResolver().insert(uri, values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
