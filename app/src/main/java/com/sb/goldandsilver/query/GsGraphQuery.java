package com.sb.goldandsilver.query;

import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
public class GsGraphQuery implements I_Query{
    private Sequel gsGraph;

    public GsGraphQuery(Sequel aQuery){
        this.gsGraph = aQuery;
    }

    public List execute() {return gsGraph.selectGraph();}

}
