package com.sb.goldandsilver.mvctext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class GsTextAdapter extends BaseAdapter {
    private final Context mContext;

    private List<GsTextItem> mGsTextItem;

    public GsTextAdapter(Context context, List<GsTextItem> data) {
        this.mContext= context;
        this.mGsTextItem = data;
    }
    @Override
    public int getCount() { return mGsTextItem.size(); }
    @Override
    public Object getItem (int position) { return mGsTextItem.get(position); }
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
        String METALGOLDFORMAT= "Gold(%s) : %s";
        String METALSILVERFORMAT= "Silver : %s";

        GsTextView itemView;

        GsTextItem aItem= mGsTextItem.get(position);

        if(convertView== null) {
            itemView= new GsTextView(mContext, aItem);

        } else {
            itemView= (GsTextView)convertView;
        }

        itemView.setTimeTextView(String.format(TIMEFORMAT, aItem.getTime()));
        itemView.setGoldAmTextView(String.format(METALGOLDFORMAT, "AM", aItem.getGoldAm()));
        itemView.setGoldPmTextView(String.format(METALGOLDFORMAT, "PM", aItem.getGoldPm()));
        itemView.setSilverTextView(String.format(METALSILVERFORMAT, aItem.getSilver()));

        // Return view
        return itemView;
    }


}
