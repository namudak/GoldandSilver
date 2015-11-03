package com.sb.goldandsilver.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sb.goldandsilver.R;


/**
 * Created by namudak on 2015-09-14.
 */
public class GoldSilverView extends LinearLayout{

    private TextView mTimeTextView;
    private TextView mGoldTextView;
    private TextView mSilverTextView;

    public GoldSilverView(Context context) {
        super(context);
    }

    public GoldSilverView(Context context, GoldSilverItem aItem) {
        super(context);

        LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.goldsilveritem_listview, this, true);

        mTimeTextView = (TextView)findViewById(R.id.time_text_view);
        mTimeTextView.setText(aItem.getTime());
        mGoldTextView = (TextView)findViewById(R.id.gold_text_view);
        mGoldTextView.setText(aItem.getGoldAm());
        mSilverTextView = (TextView)findViewById(R.id.silver_text_view);
        mSilverTextView.setText(aItem.getSilver());
    }

    // Set time textview as customed
    public void setTimeTextView(String time) {
        mTimeTextView.setText(time);}
    // Set Gold textview as customed
    public void setGoldTextView(String goldam) {
        mGoldTextView.setText(goldam);}
    // set Silver textview as customed
    public void setSilverTextView(String silver) {
        mSilverTextView.setText(silver);}

}
