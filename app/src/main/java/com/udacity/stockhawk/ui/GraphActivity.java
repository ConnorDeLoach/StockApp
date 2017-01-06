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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity implements IAxisValueFormatter {
    private Intent mIntent;
    private ArrayList<Entry> mEntries = new ArrayList<>();
    private String mValues;
    private String mSymbol;


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
        // Store index as float paired with price
        String[] a = mValues.split(",");
        for (int i = 0; i < a.length; i += 2) {
            Entry entry = new Entry((float) i, (float) i + 2);
            mEntries.add(entry);
        }

        //Line chart
        LineChart mChart = (LineChart) findViewById(R.id.linechart);
        LineDataSet lineDataSet = new LineDataSet(mEntries, "Price");
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextColor(0xffffffff);
        mChart.setData(lineData);

        // Format x-axis label
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(0xffffffff);

        // Format y-axis
        YAxis leftYAxis = mChart.getAxisLeft();
        YAxis rightYAxis = mChart.getAxisRight();
        leftYAxis.setTextColor(0xffffffff);
        rightYAxis.setTextColor(0xffffffff);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return null;
    }
}