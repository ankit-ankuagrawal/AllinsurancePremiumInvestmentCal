package com.godavari.premiuminvestmentcal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.godavari.premiuminvestmentcal.interfaces.ViewListsHelperInterface;
import com.godavari.premiuminvestmentcal.listener.MyEditTextTextWatcher;
import com.godavari.premiuminvestmentcal.listener.MyEditTextTouchListener;
import com.godavari.premiuminvestmentcal.interfaces.CurrentViewHelperInterface;
import com.godavari.premiuminvestmentcal.R;
import com.godavari.premiuminvestmentcal.utility.DigitToWordUtility;
import com.godavari.premiuminvestmentcal.utility.MathUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements CurrentViewHelperInterface, ViewListsHelperInterface, TextWatcher {

    private static final String LOG_CAT = Main2Activity.class.getSimpleName();

    private ScrollView scrollView;
    private LinearLayout llTypeOfInsuranceParentLayout;
    private LinearLayout llAllInsuranceTotalParentLayout;
    private LinearLayout llSIPParentLayout;

    private Map<Integer, LinearLayout> generalInsuranceViewMap = new HashMap<>();

    private List<Integer> indexForLifeInsuranceList = new ArrayList<>(),
            indexForHealthInsuranceList = new ArrayList<>(),
            indexForPersonalInsuranceList = new ArrayList<>(),
            indexForOtherInsuranceView = new ArrayList<>();

    private View currentViewFocused;

    private int lifeInsuranceTotal, healthInsuranceTotal, PAInsuranceTotal, otherInsuranceTotal;
    private long totalPremium;

    private LinearLayout lifeInsuranceRowTotalLayout, healthInsuranceRowTotalLayout, paInsuranceRowTotalLayout, otherInsuranceRowTotalLayout, totalInsuranceRowTotalLayout;
    private LinearLayout sipGoalLayout, sipMonthlyLayout, sipTotalInvestedLayout, sipPercentPerMonthLayout;

    private EditText etSIPTerm, etSIPRate;
    private double sipTerm, sipRate;

    private Button bCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        llTypeOfInsuranceParentLayout = (LinearLayout) findViewById(R.id.llTypeOfInsuranceParentLayout);
        llAllInsuranceTotalParentLayout = (LinearLayout) findViewById(R.id.llAllInsuranceTotalParentLayout);
        llSIPParentLayout = (LinearLayout) findViewById(R.id.llSIPParentLayout);

        lifeInsuranceRowTotalLayout = doSomething(llAllInsuranceTotalParentLayout, R.string.life_insurance);
        healthInsuranceRowTotalLayout = doSomething(llAllInsuranceTotalParentLayout, R.string.health_insurance);
        paInsuranceRowTotalLayout = doSomething(llAllInsuranceTotalParentLayout, R.string.personal_accident_insurance);
        otherInsuranceRowTotalLayout = doSomething(llAllInsuranceTotalParentLayout, R.string.other_insurance);
        totalInsuranceRowTotalLayout = doSomething(llAllInsuranceTotalParentLayout, R.string.total_premium);

        sipGoalLayout = doSomething(llSIPParentLayout, R.string.goal_amount);
        sipMonthlyLayout = doSomething(llSIPParentLayout, R.string.sip_monthly);
        sipTotalInvestedLayout = doSomething(llSIPParentLayout, R.string.total_money_invested_through_sip);
        sipPercentPerMonthLayout = doSomething(llSIPParentLayout, R.string.per_total_amount_invested_per_month);

        etSIPRate = (EditText) findViewById(R.id.etSIPRate);
        etSIPTerm = (EditText) findViewById(R.id.etSIPTerm);

        etSIPRate.setOnTouchListener(new MyEditTextTouchListener(this));
        etSIPTerm.setOnTouchListener(new MyEditTextTouchListener(this));

        etSIPRate.addTextChangedListener(this);
        etSIPTerm.addTextChangedListener(this);

        bCal = (Button) findViewById(R.id.bCal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activitty2_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemId = item.getItemId();

        int noOfInsuranceView = llTypeOfInsuranceParentLayout.getChildCount();

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
            case R.id.miShareApp:
                shareTheApp();
                return true;
            case R.id.miInfo:
                createInfoDialogBox();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void addGeneralInsuranceRowView(String typeOfInsuranceTitle) {

        final LinearLayout generalInsuranceLayoutView = inflateLinearLayout(R.layout.general_type_insurance_layout);
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
        tvInsuranceTitle.setText(typeOfInsuranceTitle);


        //set tag for all views for general insurance layout
        // here tag will be index no. at which the general insurance layout is added
        int newGeneralInsuranceViewIndex = llTypeOfInsuranceParentLayout.getChildCount();

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
        llTypeOfInsuranceParentLayout.addView(generalInsuranceLayoutView, llTypeOfInsuranceParentLayout.getChildCount());

        //One the row is added scroll down to last
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(0, generalInsuranceLayoutView.getTop());
            }
        });


        etTerm.requestFocus();
        currentViewFocused = etTerm;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        bCal.setVisibility(View.VISIBLE);
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
        return (LinearLayout) llTypeOfInsuranceParentLayout.getChildAt(index);
    }

    @Override
    public EditText getETTermItem(int index) {
        return (EditText) getLLGeneralInsuranceItem(index).findViewById(R.id.etTerm);
    }

    @Override
    public EditText getETPremiumItem(int index) {
        return (EditText) getLLGeneralInsuranceItem(index).findViewById(R.id.etPremium);
    }

    @Override
    public TextView getTVDigitToWordItem(int index) {
        return (TextView) getLLGeneralInsuranceItem(index).findViewById(R.id.tvDigitToWord);
    }

    @Override
    public TextView getTVRowTotalItem(int index) {
        return (TextView) getLLGeneralInsuranceItem(index).findViewById(R.id.tvRowTotal);
    }

    @Override
    public TextView getTVDTWRowTotalItem(int index) {
        return (TextView) getLLGeneralInsuranceItem(index).findViewById(R.id.tvDTWRowTotal);
    }

    public void onClick(View v) {
        int imageButtonRemoveViewTag = (int) v.getTag();
        Log.i(LOG_CAT, "index to remove: " + imageButtonRemoveViewTag);
        LinearLayout generalInsuranceViewRow = (LinearLayout) v.getParent().getParent();

        llTypeOfInsuranceParentLayout.removeView(generalInsuranceViewRow);

        String insuranceTitle = ((TextView) generalInsuranceViewRow.findViewById(R.id.tvTypeOfInsuranceTitle)).getText().toString();

        if (getString(R.string.life_insurance).equals(insuranceTitle)) {
            indexForLifeInsuranceList.remove(indexForLifeInsuranceList.indexOf(imageButtonRemoveViewTag));
        } else if (getString(R.string.health_insurance).equals(insuranceTitle)) {
            indexForHealthInsuranceList.remove(indexForHealthInsuranceList.indexOf(imageButtonRemoveViewTag));
        } else if (getString(R.string.personal_accident_insurance).equals(insuranceTitle)) {
            indexForPersonalInsuranceList.remove(indexForPersonalInsuranceList.indexOf(imageButtonRemoveViewTag));
        } else if (getString(R.string.other_insurance).equals(insuranceTitle)) {
            indexForOtherInsuranceView.remove(indexForOtherInsuranceView.indexOf(imageButtonRemoveViewTag));
        }

        if (llTypeOfInsuranceParentLayout.getChildCount() == 0) {
            bCal.setVisibility(View.GONE);
            llAllInsuranceTotalParentLayout.setVisibility(View.GONE);
            llSIPParentLayout.setVisibility(View.GONE);
        }

    }

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
        llAllInsuranceTotalParentLayout.setVisibility(View.VISIBLE);
        llSIPParentLayout.setVisibility(View.VISIBLE);

        lifeInsuranceTotal = 0;
        healthInsuranceTotal = 0;
        PAInsuranceTotal = 0;
        otherInsuranceTotal = 0;
        totalPremium = 0;

        if (!indexForLifeInsuranceList.isEmpty()) {
            lifeInsuranceRowTotalLayout.setVisibility(View.VISIBLE);
            for (Integer tag : indexForLifeInsuranceList) {
                int term = Integer.valueOf(0 + getETTermItem(tag).getText().toString());
                int premium = Integer.valueOf(0 + getETPremiumItem(tag).getText().toString());

                lifeInsuranceTotal += term * premium;
            }
        } else {
            lifeInsuranceRowTotalLayout.setVisibility(View.GONE);
        }

        if (!indexForHealthInsuranceList.isEmpty()) {
            healthInsuranceRowTotalLayout.setVisibility(View.VISIBLE);
            for (Integer tag : indexForHealthInsuranceList) {
                int term = Integer.valueOf(0 + getETTermItem(tag).getText().toString());
                int premium = Integer.valueOf(0 + getETPremiumItem(tag).getText().toString());

                healthInsuranceTotal += term * premium;
            }
        } else {
            healthInsuranceRowTotalLayout.setVisibility(View.GONE);
        }

        if (!indexForPersonalInsuranceList.isEmpty()) {
            paInsuranceRowTotalLayout.setVisibility(View.VISIBLE);
            for (Integer tag : indexForPersonalInsuranceList) {
                int term = Integer.valueOf(0 + getETTermItem(tag).getText().toString());
                int premium = Integer.valueOf(0 + getETPremiumItem(tag).getText().toString());

                PAInsuranceTotal += term * premium;
            }
        } else {
            paInsuranceRowTotalLayout.setVisibility(View.GONE);
        }

        if (!indexForOtherInsuranceView.isEmpty()) {
            otherInsuranceRowTotalLayout.setVisibility(View.VISIBLE);
            for (Integer tag : indexForOtherInsuranceView) {
                int term = Integer.valueOf(0 + getETTermItem(tag).getText().toString());
                int premium = Integer.valueOf(0 + getETPremiumItem(tag).getText().toString());

                otherInsuranceTotal += term * premium;
            }
        } else {
            otherInsuranceRowTotalLayout.setVisibility(View.GONE);
        }

        totalInsuranceRowTotalLayout.setVisibility(View.VISIBLE);

        totalPremium = lifeInsuranceTotal + healthInsuranceTotal + PAInsuranceTotal + otherInsuranceTotal;

        ((TextView) lifeInsuranceRowTotalLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency(lifeInsuranceTotal));
        ((TextView) lifeInsuranceRowTotalLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", lifeInsuranceTotal));

        ((TextView) healthInsuranceRowTotalLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency(healthInsuranceTotal));
        ((TextView) healthInsuranceRowTotalLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", healthInsuranceTotal));

        ((TextView) paInsuranceRowTotalLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency(PAInsuranceTotal));
        ((TextView) paInsuranceRowTotalLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", PAInsuranceTotal));

        ((TextView) otherInsuranceRowTotalLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency(otherInsuranceTotal));
        ((TextView) otherInsuranceRowTotalLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", otherInsuranceTotal));

        ((TextView) totalInsuranceRowTotalLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency((int) totalPremium));
        ((TextView) totalInsuranceRowTotalLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", (int) totalPremium));
    }


    public void onClick2(View v) {
        onCalDone();
    }

    private LinearLayout doSomething(LinearLayout parentLayout, int rowTitle) {
        LinearLayout childLayout = inflateLinearLayout(R.layout.general_row_total_layout);
        TextView tvRowTitle = childLayout.findViewById(R.id.tvRowTitle);
        tvRowTitle.setText(getString(rowTitle));

        parentLayout.addView(childLayout);
        childLayout.setVisibility(View.GONE);
        return childLayout;
    }

    private LinearLayout inflateLinearLayout(int resourceLayoutId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (LinearLayout) inflater.inflate(resourceLayoutId, null);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        sipRate = Double.valueOf(0 + etSIPRate.getText().toString());
        sipTerm = Double.valueOf(0 + etSIPTerm.getText().toString());

        double periodicInterestRate = sipRate / 100.0d / 12.0d;
        double fv = totalPremium;

        double monthlySip = (fv / (1 + periodicInterestRate)) * (periodicInterestRate / (Math.pow(1 + periodicInterestRate, sipTerm * 12.0d) - 1));

        sipGoalLayout.setVisibility(View.VISIBLE);
        ((TextView) sipGoalLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency((int) totalPremium));
        ((TextView) sipGoalLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", (int) totalPremium));

        sipMonthlyLayout.setVisibility(View.VISIBLE);
        ((TextView) sipMonthlyLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency(MathUtility.formatDoubleToInteger(monthlySip)));
        ((TextView) sipMonthlyLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", (int) monthlySip));

        int sipTotalInvested = MathUtility.formatDoubleToInteger(MathUtility.formatDoubleToInteger(monthlySip) * 12 * sipTerm);
        sipTotalInvestedLayout.setVisibility(View.VISIBLE);
        ((TextView) sipTotalInvestedLayout.findViewById(R.id.tvRowTotal)).setText(MathUtility.formatToCurrency(sipTotalInvested));
        ((TextView) sipTotalInvestedLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", sipTotalInvested));

        double sipPercentPerMonth = MathUtility.formatDouble3DecimalValue((monthlySip / fv) * 100);
        sipPercentPerMonthLayout.setVisibility(View.VISIBLE);
        ((TextView) sipPercentPerMonthLayout.findViewById(R.id.tvRowTotal)).setText(String.valueOf(sipPercentPerMonth) + "%");
        //((TextView) sipPercentPerMonthLayout.findViewById(R.id.tvDTWRowTotal)).setText(DigitToWordUtility.convertDigitsToWords(this, "en", (int) (monthlySip / fv) * 100));

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void calculate(View v) {
        onCalDone();
        onTextChanged("", 0, 0, 0);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(0, bCal.getBottom());
            }
        });
    }

    private void createInfoDialogBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.info));
        builder.setIcon(getResources().getDrawable(R.drawable.ic_info_black_24dp));
        builder.setMessage(getString(R.string.app_info));
        builder.show();
    }

    private void shareTheApp()
    {
        String appUrl = "https://play.google.com/store/apps/details?id="+getPackageName();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Install the "+getString(R.string.app_name)+" App\n"+appUrl);
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.share_the_app)));
    }
}
