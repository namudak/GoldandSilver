package com.sb.goldandsilver.database;

import android.content.ContentValues;
import android.net.Uri;

import com.sb.goldandsilver.utility.network.NetworkUtility;

import org.json.JSONObject;

/**
 * Created by namudak on 2015-11-02.
 */
public class CurrencyUrl {
    private static final String URL_CURRENCY=
            "http://free.currencyconverterapi.com/api/v3/convert?q=%s&compact=ultra";

    public double RetrieveCurrencyData(String currencyUnit) {

        String strCurrency = String.format(URL_CURRENCY, currencyUnit);

        NetworkUtility network = new NetworkUtility();

        double value= 0.0;

        try {
            Uri uri;
            String[] valueArray = null;
            ContentValues values = new ContentValues();

            // *** Currency exchange rate *** HTTP 에서 내용을 String 으로 받아 온다
            String jsonCurrencyString = network.getResponse(strCurrency);

            JSONObject jsonCurrencyObject = new JSONObject(jsonCurrencyString);
            value= jsonCurrencyObject.getDouble(currencyUnit);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}
