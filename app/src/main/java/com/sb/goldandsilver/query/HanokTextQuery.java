package com.sb.goldandsilver.query;

import java.util.List;

/**
 * Created by namudak on 2015-10-20.
 */
public class HanokTextQuery implements I_Query{
    private Sequel hanok;

    public HanokTextQuery(Sequel aQuery){
        this.hanok = aQuery;
    }

    public List execute() {
        return hanok.selectText();
    }

}
