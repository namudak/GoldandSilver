package com.sb.goldandsilver.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.goldandsilver.provider.GSContract;
import com.sb.goldandsilver.provider.GSUrlHelper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.net.ssl.SSLContext;

/**
 * Created by namudak on 2015-09-20.
 */
public class GoldSilverUrl {
    private static final String URL_METAL_GOLD=
            "https://www.quandl.com/api/v3/datasets/LBMA/GOLD.json?auth_token=JtnQ9pvNbj8NKJfiNd_4&"+
                    "start_date=%s&end_date=%s";
    private static final String URL_METAL_SILVER=
            "https://www.quandl.com/api/v3/datasets/LBMA/SILVER.json?auth_token=JtnQ9pvNbj8NKJfiNd_4&"+
                    "start_date=%s&end_date=%s";

    Context mContext= null;
    GSUrlHelper mDbHelper= null;

    public GoldSilverUrl(Context context, GSUrlHelper helper){
        this.mContext= context;
        mDbHelper= helper;
    }

    public void RetrieveJsonData(String start, String today) {

        String strGold= String.format(URL_METAL_GOLD, start, today);
        String strSilver= String.format(URL_METAL_SILVER, start, today);

        // Create database
        GSUrlHelper helper = GSUrlHelper.getInstance(mContext);

        try {
            Uri uri;
            String[] valueArray= null;
            ContentValues values = new ContentValues();

            // *** Gold/Silver *** HTTP 에서 내용을 String 으로 받아 온다
            // Gold case
            String jsonGoldString = getResponse(strGold);

            JSONObject jsonGoldObject = new JSONObject(jsonGoldString).getJSONObject("dataset");
            JSONArray jsonGoldArray = jsonGoldObject.getJSONArray("data");

            ObjectMapper objectGoldMapper = new ObjectMapper();

            List<Gold> goldList = objectGoldMapper.readValue(jsonGoldArray.toString(),
                    objectGoldMapper.getTypeFactory().constructCollectionType(
                            List.class, Gold.class
                    ));



            // Silver case
            String jsonSilverString = getResponse(strSilver);

            JSONObject jsonSilverObject = new JSONObject(jsonSilverString).getJSONObject("dataset");
            JSONArray jsonSilverArray = jsonSilverObject.getJSONArray("data");

            ObjectMapper objectSilverMapper = new ObjectMapper();

            List<Silver> silverList = objectSilverMapper.readValue(jsonSilverArray.toString(),
                    objectSilverMapper.getTypeFactory().constructCollectionType(
                            List.class, Silver.class
                    ));

            int silverNum= 0;
            for(Gold gold : goldList) {
                values.clear();

                values.put(GSContract.Columns.TIME, gold.TIME);
                values.put(GSContract.Columns.GOLD_AM_US, gold.GOLD_AM_US);
                values.put(GSContract.Columns.GOLD_PM_US, gold.GOLD_PM_US);
                values.put(GSContract.Columns.GOLD_AM_GB, gold.GOLD_AM_GB);
                values.put(GSContract.Columns.GOLD_PM_GB, gold.GOLD_PM_GB);
                values.put(GSContract.Columns.GOLD_AM_EU, gold.GOLD_AM_EU);
                values.put(GSContract.Columns.GOLD_PM_EU, gold.GOLD_PM_EU);

                Silver silver= silverList.get(silverNum++);
                values.put(GSContract.Columns.SILVER_US, silver.SILVER_US);
                values.put(GSContract.Columns.SILVER_GB, silver.SILVER_GB);
                values.put(GSContract.Columns.SILVER_EU, silver.SILVER_EU);

                Float f1 = Float.valueOf(gold.GOLD_AM_US);
                Float f2 = Float.valueOf(silver.SILVER_US);
                String ratioStr =Float.toString(f1/ f2);
                values.put(GSContract.Columns.GSRATIO, ratioStr);

                uri = GSContract.CONTENT_URI;
                mContext.getContentResolver().insert(uri, values);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** url 로 부터 스트림을 읽어 String 으로 반환한다
     * @param url
     * @return
     * @throws IOException
     */
    private String getResponse(String url) throws IOException {

        // 클라이언트 오브젝트
        OkHttpClient okHttpClient = new OkHttpClient();
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // 시스템이 TLS를 지원하지 않습니다
        }
        okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();
    }


}
