package com.sb.goldandsilver.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sb.goldandsilver.provider.GsOpenHelper;

import org.achartengine.GraphicalView;

import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
class GsGraphTask extends AsyncTask<Void, GraphicalView, List> {

    private static final String TAG = GsGraphTask.class.getSimpleName();

    private Context mContext;

    private static GsOpenHelper mDbHelper;


    public GsGraphTask(Context context){
        mContext= context;
        mDbHelper= GsOpenHelper.getInstance(mContext);
    }

    @Override
    protected void onPreExecute() {

        try {

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected List doInBackground(Void... params) { // 첫번째 인자

        return null;
    }

    // publishUpdate로만 호출
    @Override
    protected void onProgressUpdate(GraphicalView... values) { // 두번째 인자
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List list) { // 세번째 인자



    }



}