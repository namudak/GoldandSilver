package com.sb.goldandsilver.query;

import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
public class GsGraphQuery implements I_Query{
    private Sequel goldsilver;

    public GsGraphQuery(Sequel aQuery){
        this.goldsilver = aQuery;
    }

    public List execute() {return goldsilver.selectGraph();}

}
