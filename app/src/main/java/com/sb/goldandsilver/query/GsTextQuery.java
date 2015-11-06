package com.sb.goldandsilver.query;

import java.util.List;

/**
 * Created by namudak on 2015-10-20.
 */
public class GsTextQuery implements I_Query{
    private Sequel goldsilver;

    public GsTextQuery(Sequel aQuery){
        this.goldsilver = aQuery;
    }

    public List execute() {
        return goldsilver.selectText();
    }

}
