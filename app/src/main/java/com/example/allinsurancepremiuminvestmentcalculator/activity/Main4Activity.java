package com.example.allinsurancepremiuminvestmentcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allinsurancepremiuminvestmentcalculator.R;
import com.example.allinsurancepremiuminvestmentcalculator.interfaces.CurrentViewHelperInterface;
import com.example.allinsurancepremiuminvestmentcalculator.listener.MyEditTextTextWatcher;
import com.example.allinsurancepremiuminvestmentcalculator.listener.MyEditTextTouchListener;
import com.example.allinsurancepremiuminvestmentcalculator.utility.DigitToWordUtility;

import org.w3c.dom.Text;

public class Main4Activity extends AppCompatActivity implements CurrentViewHelperInterface, TextWatcher {

    private static final String LOG_CAT = Main4Activity.class.getSimpleName();

    private TextView tvLifeInsuranceTotal, tvHealthInsuranceTotal, tvPAInsuranceTotal, tvOtherInsuranceTotal, tvAllInsuranceTotal;
    private TextView tvDTWLifeInsuranceTotal, tvDTWHealthInsuranceTotal, tvDTWPAInsuranceTotal, tvDTWOtherInsuranceTotal, tvDTWAllInsuranceTotal;

    private int lifeInsuranceTotal, healthInsuranceTotal, PAInsuranceTotal, otherInsuranceTotal;
    private long totalPremium;

    private EditText etTerm, etRate;

    private View currentViewFocused;

    private double sipTerm, sipRate;

    private TextView tvGoalAmount,tvSipMonthly,tvSipPercentagePerMonth,tvSipTotalMoneyInvested;
    private TextView tvDTWGoalAmount,tvDTWSipTotalMoneyInvested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        tvLifeInsuranceTotal = (TextView) findViewById(R.id.tvLifeInsuranceTotal);
        tvHealthInsuranceTotal = (TextView) findViewById(R.id.tvHealthInsuranceTotal);
        tvPAInsuranceTotal = (TextView) findViewById(R.id.tvPAInsuranceTotal);
        tvOtherInsuranceTotal = (TextView) findViewById(R.id.tvOtherInsuranceTotal);
        tvAllInsuranceTotal = (TextView) findViewById(R.id.tvAllInsuranceTotal);

        tvDTWLifeInsuranceTotal = (TextView) findViewById(R.id.tvDTWLifeInsuranceTotal);
        tvDTWHealthInsuranceTotal = (TextView) findViewById(R.id.tvDTWHealthInsuranceTotal);
        tvDTWPAInsuranceTotal = (TextView) findViewById(R.id.tvDTWPAInsuranceTotal);
        tvDTWOtherInsuranceTotal = (TextView) findViewById(R.id.tvDTWOtherInsuranceTotal);
        tvDTWAllInsuranceTotal = (TextView) findViewById(R.id.tvDTWAllInsuranceTotal);

        etRate = (EditText) findViewById(R.id.etRate);
        etTerm = (EditText) findViewById(R.id.etTerm);

        etTerm.setOnTouchListener(new MyEditTextTouchListener(this));
        etRate.setOnTouchListener(new MyEditTextTouchListener(this));

        etTerm.addTextChangedListener(this);
        etRate.addTextChangedListener(this);

        tvGoalAmount = (TextView)findViewById(R.id.tvGoalAmount);
        tvSipMonthly = (TextView)findViewById(R.id.tvSipMonthly);
        tvSipPercentagePerMonth = (TextView)findViewById(R.id.tvSipPercentagePerMonth);
        tvSipTotalMoneyInvested = (TextView)findViewById(R.id.tvSipTotalMoneyInvested);

        tvDTWGoalAmount = (TextView)findViewById(R.id.tvDTWGoalAmount);
        tvDTWSipTotalMoneyInvested = (TextView)findViewById(R.id.tvDTWSipTotalMoneyInvested);

        Bundle bundle = getIntent().getExtras();
        lifeInsuranceTotal = bundle.getInt(Main2Activity.LIFE_INSURANCE_TOTAL_BUNDLE, 0);
        healthInsuranceTotal = bundle.getInt(Main2Activity.HEALTH_INSURANCE_TOTAL_BUNDLE, 0);
        PAInsuranceTotal = bundle.getInt(Main2Activity.PA_INSURANCE_TOTAL_BUNDLE, 0);
        otherInsuranceTotal = bundle.getInt(Main2Activity.OTHER_INSURANCE_TOTAL_BUNDLE, 0);
        totalPremium = bundle.getLong(Main2Activity.ALL_INSURANCE_TOTAL_BUNDLE, 0);

        setVal();
    }

    private void setVal() {
        tvLifeInsuranceTotal.setText(String.valueOf(lifeInsuranceTotal));
        tvDTWLifeInsuranceTotal.setText(DigitToWordUtility.convertDigitsToWords(this, "en", lifeInsuranceTotal));

        tvHealthInsuranceTotal.setText(String.valueOf(healthInsuranceTotal));
        tvDTWHealthInsuranceTotal.setText(DigitToWordUtility.convertDigitsToWords(this, "en", healthInsuranceTotal));

        tvPAInsuranceTotal.setText(String.valueOf(PAInsuranceTotal));
        tvDTWPAInsuranceTotal.setText(DigitToWordUtility.convertDigitsToWords(this, "en", PAInsuranceTotal));

        tvOtherInsuranceTotal.setText(String.valueOf(otherInsuranceTotal));
        tvDTWOtherInsuranceTotal.setText(DigitToWordUtility.convertDigitsToWords(this, "en", otherInsuranceTotal));

        tvAllInsuranceTotal.setText(String.valueOf(totalPremium));
        tvDTWAllInsuranceTotal.setText(DigitToWordUtility.convertDigitsToWords(this, "en", (int) totalPremium));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity4_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        switch (menuItem) {
            case R.id.miLanguage:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        sipRate = Double.valueOf(0 + etRate.getText().toString());
        sipTerm = Double.valueOf(0 + etTerm.getText().toString());

        double periodicInterestRate = sipRate / 100.0d / 12.0d;
        double fv = totalPremium;

        double monthlySip = (fv / (1 + periodicInterestRate)) * (periodicInterestRate / (Math.pow(1 + periodicInterestRate, sipTerm*12.0d) - 1));

        tvGoalAmount.setText(String.valueOf(totalPremium));
        tvDTWGoalAmount.setText(DigitToWordUtility.convertDigitsToWords(this,"en",(int)totalPremium));
        tvSipMonthly.setText(String.valueOf(monthlySip));

        tvSipTotalMoneyInvested.setText(String.valueOf(monthlySip*12*sipTerm));
        tvDTWSipTotalMoneyInvested.setText(DigitToWordUtility.convertDigitsToWords(this,"en",(int)(monthlySip*12*sipTerm)));
        tvSipPercentagePerMonth.setText(String.valueOf((monthlySip/fv)*100)+"%");

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
