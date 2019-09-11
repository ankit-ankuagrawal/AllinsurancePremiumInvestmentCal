package com.example.allinsurancepremiuminvestmentcalculator.utility;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.example.allinsurancepremiuminvestmentcalculator.R;
import com.example.allinsurancepremiuminvestmentcalculator.utility.AppConstants;

public class DigitToWordUtility {

    private static String ONE_DIGIT_STRING[];
    private static String TWO_DIGIT_STRING[];
    private static String TENS_STRING[];
    private static String RUPEE_STRING;

    private static String convertTwoDigitToWord(int digits) {

        StringBuffer stringBuffer = new StringBuffer();

        if (digits >= 1 && digits <= 9) {
            stringBuffer.append(ONE_DIGIT_STRING[digits - 1] + " ");
        } else if (digits >= 11 && digits <= 19) {
            stringBuffer.append(TWO_DIGIT_STRING[(digits % 10) - 1] + " ");
        } else {
            stringBuffer.append(TENS_STRING[(digits / 10) - 1] + " ");
            if (digits % 10 != 0) {
                stringBuffer.append(ONE_DIGIT_STRING[(digits % 10) - 1] + " ");
            }
        }
        return stringBuffer.toString();
    }

    private static String convertTwoDigitToWordHindi(int digits) {

        StringBuffer stringBuffer = new StringBuffer();

        if (digits >= 1 && digits <= 9) {
            stringBuffer.append(ONE_DIGIT_STRING[digits - 1] + " ");
        } else if (digits >= 10 && digits <= 99) {
            stringBuffer.append(TWO_DIGIT_STRING[(digits) - 10] + " ");
        }
        return stringBuffer.toString();
    }

    public static String convertDigitsToWords(Context context, String language, int digits) {
        //todo please remove this is temp
        reloadData(context);

        Resources resources = context.getResources();
        StringBuffer stringBuffer = new StringBuffer();

        int noOfDigits;

        for (; digits > 0; ) {

            int getDivisor;

            noOfDigits = String.valueOf(digits).length();

            if (noOfDigits % 2 == 1) {
                if (noOfDigits > 3) {
                    getDivisor = noOfDigits - 2;
                } else {
                    getDivisor = noOfDigits - 1;
                }
            } else {
                getDivisor = noOfDigits - 1;
            }

            if (noOfDigits <= 2) {
                stringBuffer.append(twoDigitToWordConvertorFactory(language, digits));
                break;
            }

            int currentValue = (int) (digits / Math.pow(10, getDivisor));
            digits %= Math.pow(10, getDivisor);

            stringBuffer.append(twoDigitToWordConvertorFactory(language, currentValue));

            Log.i("digits", "current Value: " + currentValue + " , digits: " + digits + " noofdigits: " + noOfDigits);
            if (noOfDigits >= 8 && noOfDigits <= 9) {
                stringBuffer.append(resources.getString(R.string.crore) + " ");
            }
            if (noOfDigits >= 6 && noOfDigits <= 7) {
                stringBuffer.append(resources.getString(R.string.lakh) + " ");
            }
            if (noOfDigits >= 4 && noOfDigits <= 5) {
                stringBuffer.append(resources.getString(R.string.thousand) + " ");
            } else if (noOfDigits == 3) {
                stringBuffer.append(resources.getString(R.string.hundred) + " ");
            }
        }
        if (!TextUtils.isEmpty(stringBuffer.toString())) {
            stringBuffer.append(RUPEE_STRING);
        }
        return makeFirstCapitalLetter(stringBuffer.toString());
    }

    private static String twoDigitToWordConvertorFactory(String language, int digits) {
        String data = "";
        if (AppConstants.ENGLISH_LANGUAGE_LOCALE.equals(language)) {
            data = convertTwoDigitToWord(digits);
        } else if (AppConstants.HINDI_LANGUAGE_LOCALE.equals(language)) {
            data = convertTwoDigitToWordHindi(digits);
        }
        return data;
    }

    public static void reloadData(Context context) {
        ONE_DIGIT_STRING = context.getResources().getStringArray(R.array.one_digit_string);
        TWO_DIGIT_STRING = context.getResources().getStringArray(R.array.two_digit_string);
        TENS_STRING = context.getResources().getStringArray(R.array.tens_string);
        RUPEE_STRING = context.getResources().getString(R.string.rupee_string);
    }

    private static String makeFirstCapitalLetter(String value) {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}