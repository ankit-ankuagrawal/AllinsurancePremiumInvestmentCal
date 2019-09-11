package com.example.allinsurancepremiuminvestmentcalculator.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.allinsurancepremiuminvestmentcalculator.interfaces.ViewListsHelperInterface;
import com.example.allinsurancepremiuminvestmentcalculator.listener.MyEditTextTextWatcher;
import com.example.allinsurancepremiuminvestmentcalculator.listener.MyEditTextTouchListener;
import com.example.allinsurancepremiuminvestmentcalculator.interfaces.CurrentViewHelperInterface;
import com.example.allinsurancepremiuminvestmentcalculator.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements CurrentViewHelperInterface, ViewListsHelperInterface {

    private static final String LOG_CAT = Main2Activity.class.getSimpleName();

    private ScrollView scrollView;
    private LinearLayout parentLinearLayout;

    private List<LinearLayout> generalInsuranceViewList = new ArrayList<>();
    private List<TextView> textViewInsuranceTitleList = new ArrayList<>();
    private List<ImageButton> imageButtonRemoveViewList = new ArrayList<>();
    private List<EditText> editTextTermList = new ArrayList<>();
    private List<EditText> editTextPremiumList = new ArrayList<>();
    private List<TextView> textViewDigitToWordList = new ArrayList<>();
    private List<TextView> textViewRowTotalList = new ArrayList<>();
    private List<TextView> textViewDTWRowTotalList = new ArrayList<>();

    private List<Integer> indexForLifeInsuranceList = new ArrayList<>(),
            indexForHealthInsuranceList = new ArrayList<>(),
            indexForPersonalInsuranceList = new ArrayList<>(),
            indexForOtherInsuranceView = new ArrayList<>();

    private View currentViewFocused;

    public static final String LIFE_INSURANCE_TOTAL_BUNDLE = "LIFE_INSURANCE_TOTAL_BUNDLE";
    public static final String HEALTH_INSURANCE_TOTAL_BUNDLE = "HEALTH_INSURANCE_TOTAL_BUNDLE";
    public static final String PA_INSURANCE_TOTAL_BUNDLE = "PA_INSURANCE_TOTAL_BUNDLE";
    public static final String OTHER_INSURANCE_TOTAL_BUNDLE = "OTHER_INSURANCE_TOTAL_BUNDLE";
    public static final String ALL_INSURANCE_TOTAL_BUNDLE = "ALL_INSURANCE_TOTAL_BUNDLE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        parentLinearLayout = (LinearLayout) findViewById(R.id.llParentView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activitty2_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemId = item.getItemId();

        int noOfInsuranceView = generalInsuranceViewList.size();
        switch (menuItemId) {
            case R.id.miLifeInsurance:
                indexForLifeInsuranceList.add(noOfInsuranceView);
                addGeneralInsuranceRowView(item.getTitle().toString());
                return true;
            case R.id.miHealthInsurance:
                indexForHealthInsuranceList.add(noOfInsuranceView);
                addGeneralInsuranceRowView(item.getTitle().toString());
                return true;
            case R.id.miPersonalAccident:
                indexForPersonalInsuranceList.add(noOfInsuranceView);
                addGeneralInsuranceRowView(item.getTitle().toString());
                return true;
            case R.id.miOtherAccident:
                indexForOtherInsuranceView.add(noOfInsuranceView);
                addGeneralInsuranceRowView(item.getTitle().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void addGeneralInsuranceRowView(String typeOfInsuranceTitle) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout generalInsuranceLayoutView = (LinearLayout) inflater.inflate(R.layout.general_type_insurance_layout, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = dpToPx(12);
        layoutParams.setMargins(margin, margin, margin, 0);
        generalInsuranceLayoutView.setLayoutParams(layoutParams);

        TextView tvInsuranceTitle = (TextView) generalInsuranceLayoutView.findViewById(R.id.tvTypeOfInsuranceTitle);
        ImageButton tbRemoveView = (ImageButton) generalInsuranceLayoutView.findViewById(R.id.ibRemoveView);
        EditText etTerm = (EditText) generalInsuranceLayoutView.findViewById(R.id.etTerm);
        EditText etPremium = (EditText) generalInsuranceLayoutView.findViewById(R.id.etPremium);
        TextView tvDigitToWord = (TextView) generalInsuranceLayoutView.findViewById(R.id.tvDigitToWord);
        TextView tvRowTotal = (TextView) generalInsuranceLayoutView.findViewById(R.id.tvRowTotal);
        TextView tvDTWRowTotal = (TextView) generalInsuranceLayoutView.findViewById(R.id.tvDTWRowTotal);

        // set title for the type of insurance
        //tvInsuranceTitle.setText(typeOfInsuranceTitle);

        // add all views to different list of views
        generalInsuranceViewList.add(generalInsuranceLayoutView);
        textViewInsuranceTitleList.add(tvInsuranceTitle);
        imageButtonRemoveViewList.add(tbRemoveView);
        editTextTermList.add(etTerm);
        editTextPremiumList.add(etPremium);
        textViewDigitToWordList.add(tvDigitToWord);
        textViewRowTotalList.add(tvRowTotal);
        textViewDTWRowTotalList.add(tvDTWRowTotal);


        //set tag for all views for general insurance layout
        // here tag will be index no. at which the general insurance layout is added
        int newGeneralInsuranceViewIndex = generalInsuranceViewList.size() - 1;

        // set title for the type of insurance
        tvInsuranceTitle.setText(typeOfInsuranceTitle);

        generalInsuranceLayoutView.setTag(newGeneralInsuranceViewIndex);
        tvInsuranceTitle.setTag(newGeneralInsuranceViewIndex);
        tbRemoveView.setTag(newGeneralInsuranceViewIndex);
        etTerm.setTag(newGeneralInsuranceViewIndex);
        etPremium.setTag(newGeneralInsuranceViewIndex);
        tvDigitToWord.setTag(newGeneralInsuranceViewIndex);
        tvRowTotal.setTag(newGeneralInsuranceViewIndex);
        tvDTWRowTotal.setTag(newGeneralInsuranceViewIndex);

        //add on touch listener
        etTerm.setOnTouchListener(new MyEditTextTouchListener(this));
        etPremium.setOnTouchListener(new MyEditTextTouchListener(this));

        // add text changed listener
        etTerm.addTextChangedListener(new MyEditTextTextWatcher(this));
        etPremium.addTextChangedListener(new MyEditTextTextWatcher(this));

        // Add the new row before the add field button.
        parentLinearLayout.addView(generalInsuranceLayoutView, parentLinearLayout.getChildCount());

        //One the row is added scroll down to last
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(0, generalInsuranceLayoutView.getBottom());
            }
        });


        etTerm.requestFocus();
        currentViewFocused = etTerm;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    @Override
    public void updateCurrentView(View v) {
        currentViewFocused = v;
    }

    @Override
    public View getCurrentView() {
        return currentViewFocused;
    }

    @Override
    public LinearLayout getLLGeneralInsuranceItem(int index) {
        return (LinearLayout) generalInsuranceViewList.get(index);
    }

    @Override
    public EditText getETTermItem(int index) {
        return (EditText) editTextTermList.get(index);
    }

    @Override
    public EditText getETPremiumItem(int index) {
        return (EditText) editTextPremiumList.get(index);
    }

    @Override
    public TextView getTVDigitToWordItem(int index) {
        return (TextView) textViewDigitToWordList.get(index);
    }

    @Override
    public TextView getTVRowTotalItem(int index) {
        return (TextView) textViewRowTotalList.get(index);
    }

    @Override
    public TextView getTVDTWRowTotalItem(int index) {
        return (TextView) textViewDTWRowTotalList.get(index);
    }

    /*public void onClick(View v) {
        int imageButtonRemoveViewTag = (int) v.getTag();
        Log.i(LOG_CAT, "index to remove: " + imageButtonRemoveViewTag);
        LinearLayout generalInsuranceViewRow = (LinearLayout) v.getParent().getParent();

        parentLinearLayout.removeView(generalInsuranceViewRow);

        String insuranceTitle = ((TextView) generalInsuranceViewRow.findViewById(R.id.tvTypeOfInsuranceTitle)).getText().toString();
        Log.i(LOG_CAT, insuranceTitle);
        Log.i(LOG_CAT, "life " + Arrays.toString(indexForLifeInsuranceList.toArray()));
        Log.i(LOG_CAT, "health " + Arrays.toString(indexForHealthInsuranceList.toArray()));
        Log.i(LOG_CAT, "personal " + Arrays.toString(indexForPersonalInsuranceList.toArray()));
        Log.i(LOG_CAT, "other " + Arrays.toString(indexForOtherInsuranceView.toArray()));

        if (getString(R.string.life_insurance).equals(insuranceTitle)) {
            indexForLifeInsuranceList.remove(indexForLifeInsuranceList.indexOf(imageButtonRemoveViewTag));
        } else if (getString(R.string.health_insurance).equals(insuranceTitle)) {
            indexForHealthInsuranceList.remove(indexForHealthInsuranceList.indexOf(imageButtonRemoveViewTag));
        } else if (getString(R.string.personal_accident_insurance).equals(insuranceTitle)) {
            indexForPersonalInsuranceList.remove(indexForPersonalInsuranceList.indexOf(imageButtonRemoveViewTag));
        } else if (getString(R.string.other_insurance).equals(insuranceTitle)) {
            indexForOtherInsuranceView.remove(indexForOtherInsuranceView.indexOf(imageButtonRemoveViewTag));
        }

        Log.i(LOG_CAT, "after life " + Arrays.toString(indexForLifeInsuranceList.toArray()));
        Log.i(LOG_CAT, "after health " + Arrays.toString(indexForHealthInsuranceList.toArray()));
        Log.i(LOG_CAT, "after personal " + Arrays.toString(indexForPersonalInsuranceList.toArray()));
        Log.i(LOG_CAT, "after other " + Arrays.toString(indexForOtherInsuranceView.toArray()));

        generalInsuranceViewList.remove(generalInsuranceViewRow);
        textViewInsuranceTitleList.remove(generalInsuranceViewRow.findViewById(R.id.tvTypeOfInsuranceTitle));
        imageButtonRemoveViewList.remove(generalInsuranceViewRow.findViewById(R.id.ibRemoveView));
        editTextTermList.remove(generalInsuranceViewRow.findViewById(R.id.etTerm));
        editTextPremiumList.remove(generalInsuranceViewRow.findViewById(R.id.etPremium));
        textViewDigitToWordList.remove(generalInsuranceViewRow.findViewById(R.id.tvDigitToWord));
        textViewRowTotalList.remove(generalInsuranceViewRow.findViewById(R.id.tvRowTotal));
        textViewDTWRowTotalList.remove(generalInsuranceViewRow.findViewById(R.id.tvDTWRowTotal));
    }*/

    private int dpToPx(int dp) {
        float density = getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void onClick1(View v) {
        onCalDone();
    }

    public void onCalDone() {
        int lifeInsuranceTotal = 0;
        if (!indexForLifeInsuranceList.isEmpty()) {
            for (Integer tag : indexForLifeInsuranceList) {
                int term = Integer.valueOf(0 + editTextTermList.get(tag).getText().toString());
                int premium = Integer.valueOf(0 + editTextPremiumList.get(tag).getText().toString());

                lifeInsuranceTotal += term * premium;
            }
        }

        int healthInsuranceTotal = 0;
        if (!indexForHealthInsuranceList.isEmpty()) {
            for (Integer tag : indexForHealthInsuranceList) {
                int term = Integer.valueOf(0 + editTextTermList.get(tag).getText().toString());
                int premium = Integer.valueOf(0 + editTextPremiumList.get(tag).getText().toString());

                healthInsuranceTotal += term * premium;
            }
        }

        int PAInsuranceTotal = 0;
        if (!indexForPersonalInsuranceList.isEmpty()) {
            for (Integer tag : indexForPersonalInsuranceList) {
                int term = Integer.valueOf(0 + editTextTermList.get(tag).getText().toString());
                int premium = Integer.valueOf(0 + editTextPremiumList.get(tag).getText().toString());

                PAInsuranceTotal += term * premium;
            }
        }

        int otherInsuranceTotal = 0;
        if (!indexForOtherInsuranceView.isEmpty()) {
            for (Integer tag : indexForOtherInsuranceView) {
                int term = Integer.valueOf(0 + editTextTermList.get(tag).getText().toString());
                int premium = Integer.valueOf(0 + editTextPremiumList.get(tag).getText().toString());

                otherInsuranceTotal += term * premium;
            }
        }

        long totalPremium = lifeInsuranceTotal + healthInsuranceTotal + PAInsuranceTotal + otherInsuranceTotal;

        Bundle bundle = new Bundle();
        bundle.putInt(LIFE_INSURANCE_TOTAL_BUNDLE, lifeInsuranceTotal);
        bundle.putInt(HEALTH_INSURANCE_TOTAL_BUNDLE, healthInsuranceTotal);
        bundle.putInt(PA_INSURANCE_TOTAL_BUNDLE, PAInsuranceTotal);
        bundle.putInt(OTHER_INSURANCE_TOTAL_BUNDLE, otherInsuranceTotal);
        bundle.putLong(ALL_INSURANCE_TOTAL_BUNDLE, totalPremium);

        Intent intent = new Intent(this,Main4Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClick2(View v) {
        onCalDone();
    }
}
