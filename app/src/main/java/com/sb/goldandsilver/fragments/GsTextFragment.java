package com.sb.goldandsilver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sb.goldandsilver.R;
import com.sb.goldandsilver.mvctext.GsTextAdapter;
import com.sb.goldandsilver.query.Footman;
import com.sb.goldandsilver.query.GsTextQuery;
import com.sb.goldandsilver.query.Sequel;

import java.util.List;

/**
 * Created by namudak on 2015-11-06.
 */
public class GsTextFragment extends Fragment{

    private static final String TAG = GsTextFragment.class.getSimpleName();

    private ListView mListView;
    private List mList;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_main, container, false);

        mListView= (ListView)view.findViewById(R.id.lv_goldsilver);

        // Retrieve query result as list
        Sequel aQuery = new Sequel(getActivity());

        GsTextQuery gsTextQuery = new GsTextQuery(aQuery);

        Footman footman = new Footman();
        footman.takeQuery(gsTextQuery);

        mList= footman.placeQueries();

        GsTextAdapter adapter = new GsTextAdapter(getActivity(), mList);
        mListView.setAdapter(adapter);

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
