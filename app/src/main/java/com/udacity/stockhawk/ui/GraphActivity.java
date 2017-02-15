package com.udacity.stockhawk.ui;

/**
 * Activity for drawing stock history
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.Collections;

public class GraphActivity extends AppCompatActivity {
    private Intent mIntent;
    private ArrayList<Entry> mEntries = new ArrayList<>();
    private String mValues;
    private String mSymbol;
    private MyAxisValueFormatter mFormatter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mIntent = this.getIntent();
        mSymbol = mIntent.getStringExtra("symbol");


        // Get Cursor from db
        Cursor stock = getContentResolver().query(
                Contract.Quote.URI,
                new String[]{Contract.Quote.COLUMN_HISTORY},
                Contract.Quote.COLUMN_SYMBOL + " = ?",
                new String[]{mSymbol},
                null);
        if (stock != null) {
            stock.moveToFirst();
            mValues = stock.getString(0);
            stock.close();
        }

        // Create Entries <date - price>
        String[] valueArray = mValues.split("\\r?\\n");
        for (int i = 0; i < valueArray.length; i++) {
            String str = valueArray[i];
            String[] a = str.split(",");

            Entry entry = new Entry(Float.parseFloat(a[0]), Float.parseFloat(a[1]));
            mEntries.add(i, entry);
        }

        // Sort Entries
        Collections.sort(mEntries, new EntryXComparator());

        //Line chart
        LineChart mChart = (LineChart) findViewById(R.id.linechart);
        LineDataSet lineDataSet = new LineDataSet(mEntries, mSymbol + " Stock Price");
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextColor(0xffffffff);
        mChart.setData(lineData);
        mChart.getDescription().setEnabled(false);
        mChart.setScaleMinima(10f, 1f);

        mFormatter = new MyAxisValueFormatter();
        // Format x-axis label
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(0xffffffff);
        xAxis.setValueFormatter(mFormatter);

        // Format y-axis
        YAxis leftYAxis = mChart.getAxisLeft();
        mChart.getAxisRight().setEnabled(false);
        leftYAxis.setTextColor(0xffffffff);

        // Format legend
        Legend legend = mChart.getLegend();
        legend.setTextColor(0xffffffff);
    }
}