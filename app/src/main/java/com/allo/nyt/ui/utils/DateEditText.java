package com.allo.nyt.ui.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.allo.nyt.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Custom EditText that show a DatePickerDialog
 */
public class DateEditText extends EditText {

    public interface OnDateEditTextListener {
        void onDateChanged(Date date);
    }

    private OnDateEditTextListener mListener;

    private DatePickerDialog datePickerDialog;
    private Date minDate;
    private Date maxDate;

    private Date date;

    public DateEditText(Context context) {
        super(context);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        setGravity(Gravity.CENTER);

        setFocusable(false);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dismissKeyboard(v);
                showDatePickerFromDialog();
            }
        });
        /*
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Utils.dismissKeyboard(v);
                    performClick();
                    clearFocus();
                }
            }
        });
        */

        // Remove cursor
        setCursorVisible(false);
    }

    private void showDatePickerFromDialog() {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Convert day, month and year to Date
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                date = calendar.getTime();

                DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
                SimpleDateFormat sdf = (SimpleDateFormat) dateFormat;
                String pattern = sdf.toPattern().replaceAll("y+", "yyyy");
                sdf.applyPattern(pattern);
                setText(sdf.format(date));

                if (mListener != null) mListener.onDateChanged(date);
            }
        }, year, month, day);

        if (maxDate != null) {
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
        }
        if (minDate != null) {
            datePickerDialog.getDatePicker().setMinDate(minDate.getTime());
        }
        datePickerDialog.show();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        if (date != null) {
            DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
            setText(dateFormat.format(date));
        } else {
            setText(null);
        }
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setOnDateEditTextListener(OnDateEditTextListener listener) {
        this.mListener = listener;
    }
}
