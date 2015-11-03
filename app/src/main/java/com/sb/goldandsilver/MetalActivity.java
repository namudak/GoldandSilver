package com.sb.goldandsilver;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sb.goldandsilver.chart.LineChart;
import com.sb.goldandsilver.database.AsyncTaskActivity;
import com.sb.goldandsilver.list.GoldSilverAdapter;
import com.sb.goldandsilver.list.GoldSilverItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by namudak on 2015-09-14.
 */
public class MetalActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 77;
    private static String TAG= MetalActivity.class.getSimpleName();

    List mGsList= new ArrayList<GoldSilverItem>();

    private ListView mGoldSilverListView;
    private GoldSilverAdapter mAdapter;

    private LinearLayout mListContainer;
    private LinearLayout mChartContainer;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_metal);

        mListContainer= (LinearLayout)findViewById(R.id.list);
        mChartContainer= (LinearLayout)findViewById(R.id.chart);

        mGoldSilverListView = (ListView) findViewById(R.id.goldsilver_list_view);

        Button listButton= (Button)findViewById(R.id.list_button);
        Button chartButton= (Button)findViewById(R.id.chart_button);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListContainer.setVisibility(LinearLayout.VISIBLE);
                mChartContainer.setVisibility(LinearLayout.INVISIBLE);

                mAdapter= new GoldSilverAdapter(MetalActivity.this, mGsList);
                mGoldSilverListView.setAdapter(mAdapter);

            }
        });
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListContainer.setVisibility(LinearLayout.INVISIBLE);
                mChartContainer.setVisibility(LinearLayout.VISIBLE);

                mChartContainer.removeAllViews();
                LineChart lineChart= new LineChart(MetalActivity.this,
                                        (ArrayList<GoldSilverItem>) mGsList);
                // Add the view to the linearlayout
                mChartContainer.addView(lineChart.drawLineChart());
            }
        });

        mListContainer.setVisibility(LinearLayout.VISIBLE);
        mChartContainer.setVisibility(LinearLayout.INVISIBLE);

        // Check if apk db exist. If true, update db on today and
        // If false, copy carrying db to apk db folder.
        // update db on today.
        Intent intent= new Intent(getApplicationContext(),
                                        AsyncTaskActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        double rate= 0.0;
        String[] diffStr= null;

        // Check which request it is that we're responding to
        if (requestCode == REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle bundle= intent.getExtras();

                mGsList = (List<GoldSilverItem>)bundle.getSerializable("goldsilver");
                rate= bundle.getDouble("currency");
                diffStr= bundle.getStringArray("diffvalue");
            }
        }

        // Currency exchange rate
        TextView currencyTextView= (TextView)findViewById(R.id.tv_currency);
        currencyTextView.setText("USD_KRW : "+ rate);
        // Gold price difference on previous day
        TextView diffTextView= (TextView)findViewById(R.id.tv_diff);
        diffTextView.setText(diffStr[2]+ ":"+ diffStr[3]+ "@"+ diffStr[0]);

        mAdapter= new GoldSilverAdapter(this, mGsList);
        mGoldSilverListView.setAdapter(mAdapter);

    }

}
