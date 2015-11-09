package com.sb.goldandsilver.query;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namudak on 2015-10-20.
 */
public class Sequel {

    private Context mContext;

    public Sequel(Context context) {
        mContext= context;
    }

    public List selectGs() {

        List list= new ArrayList<>();
        try {
            list =new GsTask(mContext).execute().get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        return list;
    }

    public List selectText(){

        List list= new ArrayList<>();
        try {
            list =new GsTextTask(mContext).execute("2015").get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        return list;
    }

    public List selectGraph(){

        List list= new ArrayList<>();
        try {
            list =new GsGraphTask(mContext).execute().get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        return list;
    }

    public List selectCurrency(){

        List list= new ArrayList<>();
        try {
            list =new CurrencyTask(mContext).execute().get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        return list;
    }

}
