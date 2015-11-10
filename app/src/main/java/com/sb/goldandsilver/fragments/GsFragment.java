package com.sb.goldandsilver.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sb.goldandsilver.R;
import com.sb.goldandsilver.fragments.graph.GsLineChart;
import com.sb.goldandsilver.mvcgs.GsAdapter;
import com.sb.goldandsilver.mvcgs.GsItem;
import com.sb.goldandsilver.query.Footman;
import com.sb.goldandsilver.query.GsQuery;
import com.sb.goldandsilver.query.GsTextQuery;
import com.sb.goldandsilver.query.Sequel;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-11-07.
 */
public class GsFragment extends Fragment{

    private static final String TAG = GsFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mFiveday;
    private TextView mThirtyday;
    private TextView mOneeigtyday;
    private TextView mAyear;
    private TextView mTenyear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gs_main, container, false);

        ListView listView= (ListView)view.findViewById(R.id.lv_gs);

        // Retrieve query result as list
        Sequel aQuery = new Sequel(getActivity());
        GsQuery gsQuery = new GsQuery(aQuery);

        Footman footman = new Footman();
        footman.takeQuery(gsQuery);

        List list= footman.placeQueries();

        // test list
        list= new ArrayList<GsItem>();
        String strHeader= "November 11, 2015(Wed)!Metals!Bid(Dollar)!Change";
        list.add(strHeader);
        GsItem gsItem= new GsItem("2015-11-06", "1105.12", "1107.23", "15.24");
        list.add(gsItem);
        GsItem gsItem2= new GsItem("2015-11-07", "1105.12", "1107.23", "15.24");
        list.add(gsItem2);

        GsAdapter adapter = new GsAdapter(getActivity(), list);
        listView.setAdapter(adapter);


        // attach graph
        // Retrieve query result as list
        aQuery = new Sequel(getActivity());
        GsTextQuery GsTextQuery = new GsTextQuery(aQuery);

        footman = new Footman();
        footman.takeQuery(GsTextQuery);

        list= footman.placeQueries();

        // graph routine
        LinearLayout rootLayout = (LinearLayout) view.findViewById(R.id.gschart_container);

        GsLineChart lineChart= new GsLineChart();
        GraphicalView graphicalView= lineChart.getGraphView(getActivity(), list);

        // If the specified child already has a parent,
        // You must call removeView() on the child's parent first
        if(graphicalView.getParent()!= null) {
            ((ViewGroup) graphicalView.getParent()).removeAllViewsInLayout();
        }

        rootLayout.addView(graphicalView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            // TODO: Pull to refresh 동작시 처리 구현

            // refresh 애니메이션 종료
            mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFiveday = (TextView)view.findViewById(R.id.tv_week);
        mFiveday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBackgroundColor();
                mFiveday.setBackgroundColor(Color.CYAN);
            }
        });
        mThirtyday = (TextView)view.findViewById(R.id.tv_month);
        mThirtyday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBackgroundColor();
                mThirtyday.setBackgroundColor(Color.CYAN);
            }
        });
        mOneeigtyday = (TextView)view.findViewById(R.id.tv_halfyear);
        mOneeigtyday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBackgroundColor();
                mOneeigtyday.setBackgroundColor(Color.CYAN);
            }
        });
        mAyear = (TextView)view.findViewById(R.id.tv_year);
        mAyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBackgroundColor();
                mAyear.setBackgroundColor(Color.CYAN);
            }
        });
        mTenyear = (TextView)view.findViewById(R.id.tv_tenyear);
        mTenyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBackgroundColor();
                mTenyear.setBackgroundColor(Color.CYAN);
            }
        });

    }

    private void setDefaultBackgroundColor() {
        String strColor= "#edf6b3";
        int color= Color.parseColor(strColor);

        mFiveday.setBackgroundColor(color);
        mThirtyday.setBackgroundColor(color);
        mOneeigtyday.setBackgroundColor(color);
        mAyear.setBackgroundColor(color);
        mTenyear.setBackgroundColor(color);

    }
    
}
