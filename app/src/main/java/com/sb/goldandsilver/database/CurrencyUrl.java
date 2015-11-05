package com.sb.goldandsilver.database;

import com.sb.goldandsilver.database.api.apiurl;
import com.sb.goldandsilver.utility.network.NetworkUtility;

import org.json.JSONObject;

/**
 * Created by namudak on 2015-11-02.
 *
 */
public class CurrencyUrl {

    public double RetrieveCurrencyData(String currencyUnit) {

        String strCurrency= String.format(apiurl.URL_CURRENCY, currencyUnit);

        NetworkUtility network= new NetworkUtility();

        double value= 0.0;

        try {
            // *** Currency exchange rate *** HTTP 에서 내용을 String 으로 받아 온다
            String jsonCurrencyString= network.getResponse(strCurrency);

            JSONObject jsonCurrencyObject= new JSONObject(jsonCurrencyString);
            value= jsonCurrencyObject.getDouble(currencyUnit);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}
