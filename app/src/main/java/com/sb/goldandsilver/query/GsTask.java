package com.sb.goldandsilver.query;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by namudak on 2015-11-07.
 */
public class GsTask extends AsyncTask<Void, Void, List>{

    private Context mContext;

    public GsTask(Context context){
        mContext= context;
    }

    @Override
    protected List doInBackground(Void... params) {
        return null;
    }
}
