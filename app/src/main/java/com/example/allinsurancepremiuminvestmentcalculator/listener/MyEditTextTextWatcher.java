package com.example.allinsurancepremiuminvestmentcalculator.listener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.example.allinsurancepremiuminvestmentcalculator.interfaces.CurrentViewHelperInterface;
import com.example.allinsurancepremiuminvestmentcalculator.interfaces.ViewListsHelperInterface;
import com.example.allinsurancepremiuminvestmentcalculator.utility.DigitToWordUtility;

public class MyEditTextTextWatcher implements TextWatcher {

    private Context context;
    private CurrentViewHelperInterface currentViewHelperInterface;
    private ViewListsHelperInterface viewListsHelperInterface;

    public MyEditTextTextWatcher(Context context) {
        this.context = context;
        this.currentViewHelperInterface = (CurrentViewHelperInterface) context;
        this.viewListsHelperInterface = (ViewListsHelperInterface) context;

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int currentViewTag = (int) currentViewHelperInterface.getCurrentView().getTag();


        int term = Integer.valueOf("0" + viewListsHelperInterface.getETTermItem(currentViewTag).getText());
        int premium = Integer.valueOf("0" + viewListsHelperInterface.getETPremiumItem(currentViewTag).getText());

        TextView tvDigitToWord = viewListsHelperInterface.getTVDigitToWordItem(currentViewTag);
        tvDigitToWord.setText(DigitToWordUtility.convertDigitsToWords(context, "en", premium));


        int rowTotal = term * premium;

        TextView tvRowTotal = viewListsHelperInterface.getTVRowTotalItem(currentViewTag);
        TextView tvDTWRowTotal = viewListsHelperInterface.getTVDTWRowTotalItem(currentViewTag);

        tvRowTotal.setText(String.valueOf(rowTotal));
        tvDTWRowTotal.setText(DigitToWordUtility.convertDigitsToWords(context, "en", rowTotal));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
