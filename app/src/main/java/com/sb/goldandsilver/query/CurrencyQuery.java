package com.sb.goldandsilver.query;

import java.util.List;

/**
 * Created by namudak on 2015-11-07.
 */
public class CurrencyQuery implements I_Query{
    private Sequel currency;

    public CurrencyQuery(Sequel aQuery){
        this.currency = aQuery;
    }

    public List execute() {
        return currency.selectCurrency();
    }

}
