package com.sb.goldandsilver.mvcgs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sb.goldandsilver.R;

import java.util.List;

public class GsAdapter extends BaseAdapter {
    private final Context mContext;
    private LayoutInflater inflater;

    private List mList;

    private static final int TYPE_GSITEM = 0;
    private static final int TYPE_DIVIDER = 1;

    public GsAdapter(Context context, List data) {
        this.mContext= context;
        this.mList = data;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() { return mList.size(); }
    @Override
    public Object getItem (int position) { return mList.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof GsItem) {
            return TYPE_GSITEM;
        }
        return TYPE_DIVIDER;
    }
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
        String[] titles= new String[] {"Gold", "Silver"};

        int type= getItemViewType(position);

        if(convertView== null) {
            switch(type){
                case TYPE_DIVIDER:
                    convertView= inflater.inflate(R.layout.gs_listview_header, parent, false);
                    break;
                case TYPE_GSITEM:
                    convertView= inflater.inflate(R.layout.gs_listview, parent, false);
                    break;
            }
        }

        switch (type) {
            case TYPE_DIVIDER:
                String str= (String)mList.get(0);
                TextView header= (TextView)convertView.findViewById(R.id.tv_header);
                header.setText(str);
                break;
            case TYPE_GSITEM:
                GsItem aItem = (GsItem)mList.get(position);
                TextView title= (TextView)convertView.findViewById(R.id.tv_title);
                TextView price= (TextView)convertView.findViewById(R.id.tv_price);
                TextView pricechange= (TextView)convertView.findViewById(R.id.tv_pricechange);
                TextView percentchange= (TextView)convertView.findViewById(R.id.tv_percentchange);
                title.setText(titles[position- 1]);
                switch(position){
                    case 1:
                        price.setText(aItem.getGoldAm());
                        pricechange.setText("-10.123");
                        percentchange.setText("-1.56%");
                        break;
                    case 2:
                        price.setText(aItem.getSilver());
                        pricechange.setText("-0.545");
                        percentchange.setText("-2.123%");
                        break;
                }

        }
        // Return view
        return convertView;
    }


}
