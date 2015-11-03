package com.sb.goldandsilver.api;

/**
 * Created by namudak on 2015-11-03.
 *
 */
public class apiurl {
    // www.quandl.com api url for gold
    public static final String URL_METAL_GOLD=
            "https://www.quandl.com/api/v3/datasets/LBMA/GOLD.json?auth_token=JtnQ9pvNbj8NKJfiNd_4&"+
                    "start_date=%s&end_date=%s";
    // www.quandl.com api url for silver
    public static final String URL_METAL_SILVER=
            "https://www.quandl.com/api/v3/datasets/LBMA/SILVER.json?auth_token=JtnQ9pvNbj8NKJfiNd_4&"+
                    "start_date=%s&end_date=%s";
    // free.currencyconverterapi.com api url
    public static final String URL_CURRENCY=
            "http://free.currencyconverterapi.com/api/v3/convert?q=%s&compact=ultra";
}
