package com.sb.goldandsilver.mvctext;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sb.goldandsilver.R;


/**
 * Created by namudak on 2015-09-14.
 */
public class GsTextView extends LinearLayout{

    private TextView mTimeTextView;
    private TextView mGoldAmTextView;
    private TextView mGoldPmTextView;
    private TextView mSilverTextView;

    public GsTextView(Context context) {
        super(context);
    }

    public GsTextView(Context context, GsTextItem aItem) {
        super(context);

        LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.goldsilveritem_listview, this, true);

        mTimeTextView = (TextView)findViewById(R.id.tv_time);
        mTimeTextView.setText(aItem.getTime());
        mGoldAmTextView = (TextView)findViewById(R.id.tv_goldam);
        mGoldAmTextView.setText(aItem.getGoldAm());
        mGoldPmTextView = (TextView)findViewById(R.id.tv_goldpm);
        mGoldPmTextView.setText(aItem.getGoldPm());
        mSilverTextView = (TextView)findViewById(R.id.tv_silver);
        mSilverTextView.setText(aItem.getSilver());
    }

    // Set time textview as customed
    public void setTimeTextView(String time) {
        mTimeTextView.setText(time);}
    // Set Gold textview as customed
    public void setGoldAmTextView(String goldam) {
        mGoldAmTextView.setText(goldam);}
    // Set Gold textview as customed
    public void setGoldPmTextView(String goldpm) {
        mGoldPmTextView.setText(goldpm);}
    // set Silver textview as customed
    public void setSilverTextView(String silver) {
        mSilverTextView.setText(silver);}

}
