package com.sb.goldandsilver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sb.goldandsilver.R;
import com.sb.goldandsilver.fragments.graph.GsLineChart;
import com.sb.goldandsilver.query.Footman;
import com.sb.goldandsilver.query.GsTextQuery;
import com.sb.goldandsilver.query.Sequel;

import org.achartengine.GraphicalView;

import java.util.List;

/**
 * Created by namudak on 2015-10-18.
 */
public class GsGraphFragment extends Fragment {

    private static final String TAG = GsTextFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_main, container, false);

        // Retrieve query result as list
        Sequel aQuery = new Sequel(getActivity());

        GsTextQuery GsTextQuery = new GsTextQuery(aQuery);

        Footman footman = new Footman();
        footman.takeQuery(GsTextQuery);

        List list= footman.placeQueries();

        // graph routine
        ViewGroup rootLayout = (ViewGroup) view.findViewById(R.id.chart_container);

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

    }

}
