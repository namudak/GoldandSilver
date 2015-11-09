package com.sb.goldandsilver.mvccurrency;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class CurrencyAdapter extends BaseAdapter {
    private final Context mContext;

    private List<CurrencyItem> mCurrencyItem;

    public CurrencyAdapter(Context context, List<CurrencyItem> data) {
        this.mContext= context;
        this.mCurrencyItem = data;
    }
    @Override
    public int getCount() { return mCurrencyItem.size(); }
    @Override
    public Object getItem (int position) { return mCurrencyItem.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    /**
     * Item's layout
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        String FORMAT= "%s";

        CurrencyView itemView;

        CurrencyItem aItem= mCurrencyItem.get(position);

        if(convertView== null) {
            itemView= new CurrencyView(mContext, aItem);

        } else {
            itemView= (CurrencyView)convertView;
        }

        itemView.setFlag(aItem.getFlag());
        itemView.setCurrencyId(String.format(FORMAT, aItem.getCurrencyId()));
        itemView.setName(String.format(FORMAT, aItem.getName()));
        itemView.setAlpha3(String.format(FORMAT, aItem.getAlpha3()));
        itemView.setId(String.format(FORMAT, aItem.getId()));

        // Return view
        return itemView;
    }


}
