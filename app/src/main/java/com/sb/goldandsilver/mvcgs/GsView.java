package com.sb.goldandsilver.mvcgs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sb.goldandsilver.R;


/**
 * Created by namudak on 2015-09-14.
 */
public class GsView extends LinearLayout{

    private TextView mTimeTextView;

    private TextView mHeaderTextView;

    private TextView mTitleTextView;
    private TextView mPriceTextView;
    private TextView mPriceChangeTextView;

    public GsView(Context context) {
        super(context);
    }

    public GsView(Context context, GsItem aItem) {
        super(context);

        LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.gs_listview, this, true);

        mTimeTextView = (TextView)findViewById(R.id.tv_time);
        mTitleTextView= (TextView)findViewById(R.id.tv_title);
        mPriceTextView = (TextView)findViewById(R.id.tv_price);
        mPriceChangeTextView= (TextView)findViewById(R.id.tv_pricechange);
    }

    public void setHeaderTextView(String header) {
        mHeaderTextView.setText(header);
    }
    // Set time textview as customed
    public void setTimeTextView(String time) {
        mTimeTextView.setText(time);}
    // Set title textview as customed
    public void setTitleTextView(String title) {

        mTitleTextView.setText(title);
    }
    // Set price textview as customed
    public void setPriceTextView(String price) {
        mPriceTextView.setText(price);
    }

    public void setPriceChangeTextView(String change) {
        mPriceChangeTextView.setText(change);
    }

}
