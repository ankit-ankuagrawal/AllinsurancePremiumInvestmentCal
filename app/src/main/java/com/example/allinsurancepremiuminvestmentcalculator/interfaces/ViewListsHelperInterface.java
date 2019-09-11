package com.example.allinsurancepremiuminvestmentcalculator.interfaces;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public interface ViewListsHelperInterface {
    LinearLayout getLLGeneralInsuranceItem(int index);
    EditText getETTermItem(int index);
    EditText getETPremiumItem(int index);
    TextView getTVDigitToWordItem(int index);
    TextView getTVRowTotalItem(int index);
    TextView getTVDTWRowTotalItem(int index);
}
