package com.sb.goldandsilver.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class GoldSilverAdapter extends BaseAdapter {
    private final Context mContext;

    private List<GoldSilverItem> mGoldSilverItem;

    public GoldSilverAdapter(Context context, List<GoldSilverItem> data) {
        this.mContext= context;
        this.mGoldSilverItem = data;
    }
    @Override
    public int getCount() { return mGoldSilverItem.size(); }
    @Override
    public Object getItem (int position) { return mGoldSilverItem.get(position); }
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
        String TIMEFORMAT= "%s";
        String METALFORMAT= "%s";

        GoldSilverView itemView;

        GoldSilverItem aItem= mGoldSilverItem.get(position);

        if(convertView== null) {
            itemView= new GoldSilverView(mContext, aItem);

        } else {
            itemView= (GoldSilverView)convertView;
        }

        itemView.setTimeTextView(String.format(TIMEFORMAT, aItem.getTime()));
        itemView.setGoldTextView(String.format("Gold Price : "+ METALFORMAT, aItem.getGoldAm()));
        itemView.setSilverTextView(String.format("Silver Price : "+ METALFORMAT, aItem.getSilver()));

        // Return view
        return itemView;
    }


}
