package com.godavari.premiuminvestmentcal.interfaces;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public interface ViewListsHelperInterface {
    LinearLayout getLLGeneralInsuranceItem(int index);
    EditText getETTermItem(int index);
    EditText getETPremiumItem(int index);
    TextView getTVDigitToWordItem(int index);
    TextView getTVRowTotalItem(int index);
    TextView getTVDTWRowTotalItem(int index);
}
