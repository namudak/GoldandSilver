package com.sb.goldandsilver.query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.sb.goldandsilver.provider.GsOpenHelper;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
class GsGraphTask extends AsyncTask<Void, GraphicalView, List> {

    private static final String TAG = GsGraphTask.class.getSimpleName();

    private Context mContext;

    private static GsOpenHelper mDbHelper;

    private String[] GROUPFORMAT= {
            "⑴ 한옥 건립일 현황 도표",
            "⑵ 북촌 한옥 현황 도표",
            "⑶ 한옥 문화재 현황 도표",
            "⑷ 한옥 수선 현황 도표",
            "⑸ 대지 구간 별 한옥 현황 도표",
            "⑹ 대지 구간 별 평균 대지면적,\n"+
            "건축면적, 연면적 현황 도표",
            "⑺ 대지 구간 별 평균 대지면적,\n" +
            "건축면적, 건폐율 현황 도표",
            "⑻ 대지 구간 별 평균 대지면적,\n" +
            "연면적, 용적율 현황 도표",
            "⑼ 용도 별 북촌 한옥 현황 도표",
            "⑽ 구조 별 북촌 한옥 현황 도표"

    };
    private String CHILDFORMAT[]= {
            "등록 번호 : %s",
            "대지 면적(㎡) : %s",
            "건축 면적(㎡) : %s",
            "연 면적(㎡) : %s",
            "용적율(㎡) : %s",
            "건폐율(％) : %s",
            "용도 : %s",
            "구조 : %s",
            "법정동 : %s"
    };

    List<String> groupItem = new ArrayList<>();
    List<Object> childItem = new ArrayList<>();

    // Number of hanok along plottage
    private String[] mLevel= {"0~30(㎡)", "30~60(㎡)", "60~90(㎡)",
                    "90`120(㎡)", "120~240(㎡)", "240(㎡)~"};
    private int[] mCountNum= new int[mLevel.length+ 1];

    private int mHanokTotal= 0;
    private int mBukchonHanokTotal= 0;

    public GsGraphTask(Context context){
        mContext= context;

        mDbHelper= GsOpenHelper.getInstance(mContext);
    }

    @Override
    protected void onPreExecute() {

        try {

            List<String> childList= getPlottageQuery();

            // Set groupItem as formated
            for(int i= 0; i< GROUPFORMAT.length; i++) {
                groupItem.add(String.format(GROUPFORMAT[i]));
            }
            // Sum of hanok in seoul
            mHanokTotal= getTotalHanokQuery();
            // Sum of hanok in seoul
            mBukchonHanokTotal = getTotalBukchonHanokQuery();

            for(int i= 0; i< GROUPFORMAT.length; i++) {
                List<Object> graph = new ArrayList<>();
                switch (i) {
                }
                childItem.add(graph);

            }


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected List doInBackground(Void... params) { // 첫번째 인자

        List<Object> list= new ArrayList<>();
        list.add(groupItem);
        list.add(childItem);

        return list;
    }

    // publishUpdate로만 호출
    @Override
    protected void onProgressUpdate(GraphicalView... values) { // 두번째 인자

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List list) { // 세번째 인자

        groupItem= new ArrayList<>();
        childItem= new ArrayList<>();

    }

    /**
     * get Db List for 'hanok' on plottage
     */
    private List getPlottageQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYREALPLOTTAGE],
                null
        );


        cursor.close();

        return  null;
    }

    /**
     * store plottage count to array
     */
    private void putCountArray(Float plottage) {

        if(plottage< 30.0 ) {
            mCountNum[1]+= 1;
        } else if(plottage< 60.0) {
            mCountNum[2]+= 1;
        } else if(plottage< 90.0) {
            mCountNum[3] += 1;
        } else if(plottage< 120.0) {
            mCountNum[4]+= 1;
        } else if(plottage< 240.0) {
            mCountNum[5]+= 1;
        } else {
            mCountNum[6]+= 1;
        }

    }

    /**
     * get total number of enrolled hanok
     *
     */
    private int getTotalHanokQuery() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                QueryContract.mQuery[
                        QueryContract.QUERYHANOK],
                null
        );

        int totalHanok= 0;
        while (cursor.moveToNext()) {
            totalHanok= cursor.getInt(0);//count
        }

        cursor.close();

        return totalHanok;
    }

    /**
     * get total number of enrolled hanok
     *
     */
    private int getTotalBukchonHanokQuery() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                QueryContract.mQuery[
                        QueryContract.QUERYBUKCHONHANOK],
                null
        );

        int totalBukchonHanok= 0;
        while (cursor.moveToNext()) {
            totalBukchonHanok= cursor.getInt(0);//count
        }

        cursor.close();

        return totalBukchonHanok;
    }


    /**
     * get Db List for 'repair_hanok' on sn
     */
    private List getSnQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYSN],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String val[]= new String[2];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(0);// yyyy
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }
        cursor.close();

        return  childList;
    }

    /**
     * get hanok count on level
     *
     */
    private List getCountHanok() {

        ArrayList<String> childList = new ArrayList<>();

        String val[]= new String[2];
        for(int i= 0; i< mCountNum.length- 1; i++){
            val[0]= mLevel[i];
            val[1]= String.valueOf(mCountNum[i+ 1]);

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }

        return childList;

    }
    /**
     * get hanok posttage, buildarea, floorarea, coverageratio, floorratio
     *
     */
    private List getAvg(int num, List<String> list) {

        String[] parm = new String[9];
        // 대지 1, 건축 2, 연면적 3, 용적율 4, 건폐율 5
        float val= 0.0f;
        float avg= 0.0f;

        int from, to;
        ArrayList<String> childList = new ArrayList<>();
        for (int j = 0; j < mCountNum[1]; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[1];

        childList.add(mLevel[0]+ ","+ String.format("%d", (int)avg));

        from = mCountNum[1];
        to = from + mCountNum[2];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[2];

        childList.add(mLevel[1]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[3];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[3];

        childList.add(mLevel[2]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[4];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[4];

        childList.add(mLevel[3]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[5];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[5];

        childList.add(mLevel[4]+ ","+ String.format("%d", (int)avg));

        from= to; to= from+ mCountNum[6];
        val= 0.0f;
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[6];

        childList.add(mLevel[5]+ ","+ String.format("%d", (int)avg));

        return childList;

    }

    /**
     * get hanok count on use
     *
     */
    private List getUseHanok() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYUSE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String val[]= new String[2];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(0);// use
            if(val[0].length()== 0) {
                val[0]= "기타";
            }
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    val[0] + "," + val[1]
            );
        }
        cursor.close();

        return  childList;

    }

    /**
     * get hanok count on structure
     *
     */
    private List getStructureHanok() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYSTRUCTURE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String val[]= new String[2];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(0);// structure
            if(val[0].length()== 0) {
                val[0]= "기타";
            } else {
                val[0]= val[0].replace(",", "/");
            }
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    val[0]+ ","+ val[1]
            );
        }
        cursor.close();

        return  childList;

    }

}