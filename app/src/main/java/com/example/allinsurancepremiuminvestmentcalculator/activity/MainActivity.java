package com.example.allinsurancepremiuminvestmentcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allinsurancepremiuminvestmentcalculator.R;

import java.util.Arrays;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_CAT = MainActivity.class.getName();
    private EditText etLic, etStar, etPA, etInvestmentYears, etRate;

    private int[] licAgeArray, starAgeArray, paAgeArray;
    private int[] licPremiumArray, starPremiumArray, paPremiumArray;

    private String licString, starString, paString;

    private double licTotalPremium, starTotalPremium, paTotalPremium, allTotalPremium, investmentYears, rate;

    private TextView tvLicTotalPremium, tvStarTotalPremium, tvPATotalPremium, tvAllTotalPremium, tvSipMonthly,tvSipTotalMoneyInvested,tvSipPerPerMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLic = (EditText) findViewById(R.id.etLic);
        etStar = (EditText) findViewById(R.id.etStar);
        etPA = (EditText) findViewById(R.id.etPA);

        tvLicTotalPremium = (TextView) findViewById(R.id.tvLicTotalPremium);
        tvStarTotalPremium = (TextView) findViewById(R.id.tvStarTotalPremium);
        tvPATotalPremium = (TextView) findViewById(R.id.tvPATotalPremium);
        tvAllTotalPremium = (TextView) findViewById(R.id.tvAllTotalPremium);

        etInvestmentYears = (EditText) findViewById(R.id.etInvestmentYears);
        etRate = (EditText) findViewById(R.id.etRate);
        tvSipMonthly = (TextView) findViewById(R.id.tvSipMonthly);
        tvSipTotalMoneyInvested = (TextView) findViewById(R.id.tvSipTotalMoneyInvested);
        tvSipPerPerMonth = (TextView) findViewById(R.id.tvSipPerPerMonth);
    }

    public void onClick(View v) {
        doCal();
    }

    private void doCal() {
        licString = etLic.getText().toString();
        starString = etStar.getText().toString();
        paString = etPA.getText().toString();

        StringTokenizer licTokenizer = new StringTokenizer(licString, ",");
        int licPairCount = licTokenizer.countTokens() / 2;
        licAgeArray = new int[licPairCount];
        licPremiumArray = new int[licPairCount];

        StringTokenizer starTokenizer = new StringTokenizer(starString, ",");
        int starPairCount = starTokenizer.countTokens() / 2;
        starAgeArray = new int[starPairCount];
        starPremiumArray = new int[starPairCount];

        StringTokenizer paTokenizer = new StringTokenizer(paString, ",");
        int paPairCount = paTokenizer.countTokens() / 2;
        paAgeArray = new int[paPairCount];
        paPremiumArray = new int[paPairCount];

        for (int i = 0; i < licPairCount; i++) {
            licAgeArray[i] = Integer.valueOf((String) licTokenizer.nextElement());
            licPremiumArray[i] = Integer.valueOf((String) licTokenizer.nextElement());
        }

        for (int i = 0; i < starPairCount; i++) {
            starAgeArray[i] = Integer.valueOf((String) starTokenizer.nextElement());
            starPremiumArray[i] = Integer.valueOf((String) starTokenizer.nextElement());
        }

        for (int i = 0; i < paPairCount; i++) {
            paAgeArray[i] = Integer.valueOf((String) paTokenizer.nextElement());
            paPremiumArray[i] = Integer.valueOf((String) paTokenizer.nextElement());
        }

        Log.i(LOG_CAT, "LIC: " + Arrays.toString(licAgeArray) + " " + Arrays.toString(licPremiumArray));
        Log.i(LOG_CAT, "Star: " + Arrays.toString(starAgeArray) + " " + Arrays.toString(starPremiumArray));
        Log.i(LOG_CAT, "PA: " + Arrays.toString(paAgeArray) + " " + Arrays.toString(paPremiumArray));

        for (int i = 0; i < licAgeArray.length; i++) {
            licTotalPremium += (licAgeArray[i] * licPremiumArray[i]);
        }

        for (int i = 0; i < starPremiumArray.length; i++) {
            starTotalPremium += (starAgeArray[i] * starPremiumArray[i]);
        }

        for (int i = 0; i < licAgeArray.length; i++) {
            paTotalPremium += (paAgeArray[i] * paPremiumArray[i]);
        }

        allTotalPremium = licTotalPremium + starTotalPremium + paTotalPremium;

        tvLicTotalPremium.setText(String.valueOf(licTotalPremium));
        tvStarTotalPremium.setText(String.valueOf(starTotalPremium));
        tvPATotalPremium.setText(String.valueOf(paTotalPremium));
        tvAllTotalPremium.setText(String.valueOf(allTotalPremium));

    }

    public void onClick1(View v)
    {
        calSip();
    }

    private double calSip() {

        investmentYears = Double.valueOf("0" + etInvestmentYears.getText().toString());
        rate = Double.valueOf("0" + etRate.getText().toString());

        double periodicInterestRate = rate / 100.0d / 12.0d;
        double fv = allTotalPremium;

        double monthlySip = (fv / (1 + periodicInterestRate)) * (periodicInterestRate / (Math.pow(1 + periodicInterestRate, investmentYears*12.0d) - 1));
        tvSipMonthly.setText(String.valueOf(monthlySip));

        tvSipTotalMoneyInvested.setText(String.valueOf(monthlySip*12*investmentYears));
        tvSipPerPerMonth.setText(String.valueOf((monthlySip/fv)*100)+"%");
        return monthlySip;
    }
}

