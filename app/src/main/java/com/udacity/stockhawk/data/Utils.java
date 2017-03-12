package com.udacity.stockhawk.data;

import android.content.Context;
import android.content.Intent;

import com.udacity.stockhawk.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility functions
 */

public class Utils {

    private Utils() {
    }

    static public DecimalFormat getDollarFormat() {
        return (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    }

    public static DecimalFormat getDollarFormatWithPlus() {
        DecimalFormat dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");

        return dollarFormatWithPlus;
    }

    static public DecimalFormat getPercentageFormat() {
        DecimalFormat percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");

        return percentageFormat;
    }

    public static void notifyWidgetDataSetChanged(Context context) {
        Intent dataUpdatedIntent = new Intent(context.getString(R.string.action_data_updated));
        context.sendBroadcast(dataUpdatedIntent);
    }
}