package com.sb.goldandsilver.query;

import java.util.List;

/**
 * Created by namudak on 2015-11-07.
 */
public class GsQuery implements I_Query{
    private Sequel gs;

    public GsQuery(Sequel aQuery){
        this.gs = aQuery;
    }

    public List execute() {
        return gs.selectGs();
    }
}
