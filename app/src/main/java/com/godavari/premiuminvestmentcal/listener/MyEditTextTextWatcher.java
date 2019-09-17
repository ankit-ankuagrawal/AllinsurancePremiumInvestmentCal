package com.godavari.premiuminvestmentcal.listener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.godavari.premiuminvestmentcal.interfaces.CurrentViewHelperInterface;
import com.godavari.premiuminvestmentcal.interfaces.ViewListsHelperInterface;
import com.godavari.premiuminvestmentcal.utility.DigitToWordUtility;
import com.godavari.premiuminvestmentcal.utility.MathUtility;

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
        //int currentViewTag = (int)((LinearLayout)currentViewHelperInterface.getCurrentView().getParent().getParent()).getTag();


        int term = Integer.valueOf("0" + viewListsHelperInterface.getETTermItem(currentViewTag).getText());
        int premium = Integer.valueOf("0" + viewListsHelperInterface.getETPremiumItem(currentViewTag).getText());

        TextView tvDigitToWord = viewListsHelperInterface.getTVDigitToWordItem(currentViewTag);
        tvDigitToWord.setText(DigitToWordUtility.convertDigitsToWords(context, "en", premium));


        int rowTotal = term * premium;

        TextView tvRowTotal = viewListsHelperInterface.getTVRowTotalItem(currentViewTag);
        TextView tvDTWRowTotal = viewListsHelperInterface.getTVDTWRowTotalItem(currentViewTag);

        tvRowTotal.setText(MathUtility.formatToCurrency(rowTotal));
        tvDTWRowTotal.setText(DigitToWordUtility.convertDigitsToWords(context, "en", rowTotal));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
