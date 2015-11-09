package com.sb.goldandsilver.mvccurrency;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sb.goldandsilver.R;


/**
 * Created by namudak on 2015-09-14.
 */
public class CurrencyView extends LinearLayout{

    private ImageView mFlag;
    private TextView mCurrencyId;
    private TextView mName;
    private TextView mAlpha3;
    private TextView mId;

    public CurrencyView(Context context) {
        super(context);
    }

    public CurrencyView(Context context, CurrencyItem aItem) {
        super(context);

        LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(
                                                Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.currency_listview, this, true);

        mFlag= (ImageView)findViewById(R.id.iv_flag);
        mFlag.setImageBitmap(BitmapFactory.decodeByteArray(
                aItem.getFlag(), 0, aItem.getFlag().length
        ));
        mCurrencyId = (TextView)findViewById(R.id.tv_currencyId);
        mCurrencyId.setText(aItem.getCurrencyId());
        mName = (TextView)findViewById(R.id.tv_name);
        mName.setText(aItem.getName());
        mAlpha3 = (TextView)findViewById(R.id.tv_alpha3);
        mAlpha3.setText(aItem.getAlpha3());
        mId = (TextView)findViewById(R.id.tv_id);
        mId.setText(aItem.getId());
    }

    public void setFlag(byte[] flag) {
        mFlag.setImageBitmap(BitmapFactory.decodeByteArray(
              flag, 0, flag.length
        ));
    }

    public void setCurrencyId(String currencyId) {
        mCurrencyId.setText(currencyId);
    }

    public void setName(String name) {
        mName.setText(name);
    }

    public void setAlpha3(String alpha3) {
        mAlpha3.setText(alpha3);
    }

    public void setId(String id) {
        mId.setText(id);
    }



}
