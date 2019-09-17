package com.godavari.premiuminvestmentcal.utility;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MathUtility {

    public static String formatToCurrency(int value) {
        try {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            return formatter.format(value);
        } catch (Exception e) {
            return "0";
        }
    }

    public static int formatDoubleToInteger(double price) {
        try {
            return Integer.valueOf(new DecimalFormat("##").format(price));
        } catch (Exception e) {
            return 0;
        }
    }

    public static double formatDouble3DecimalValue(double price) {
        try {
            return Double.valueOf(new DecimalFormat("##.###").format(price));
        } catch (Exception e) {
            return 0;
        }
    }
}
