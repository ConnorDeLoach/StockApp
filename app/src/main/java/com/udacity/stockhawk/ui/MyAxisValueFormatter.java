package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Calendar;

/**
 * Class to format XAxis values for GraphActivity
 */

public class MyAxisValueFormatter implements IAxisValueFormatter {

    public MyAxisValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) value);

        int mYear = calendar.get(Calendar.YEAR);
        mYear -= 2000;
        int mMonth = calendar.get(Calendar.MONTH);
        mMonth += 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        return mMonth + "/" + mDay + "/" + mYear;
    }
}
