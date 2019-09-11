package com.example.allinsurancepremiuminvestmentcalculator.listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.allinsurancepremiuminvestmentcalculator.interfaces.CurrentViewHelperInterface;
import com.google.android.material.textfield.TextInputEditText;

public class MyEditTextTouchListener implements View.OnTouchListener {

    private CurrentViewHelperInterface currentViewHelperInterface;

    public MyEditTextTouchListener(CurrentViewHelperInterface currentViewHelperInterface)
    {
        this.currentViewHelperInterface = currentViewHelperInterface;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        currentViewHelperInterface.updateCurrentView(view);
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getX() >= (view.getRight() - ((EditText) view).getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                // your action here
                ((TextInputEditText) view).setText("");
                return false;
            }

        }
        return false;
    }
}
