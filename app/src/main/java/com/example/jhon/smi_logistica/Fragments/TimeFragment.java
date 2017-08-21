package com.example.jhon.smi_logistica.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by jhon on 8/06/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    public interface OnTimeI{
      void onTimeSetInterface(int hour, int minute);
    }

    OnTimeI onTimeI;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        onTimeI.onTimeSetInterface(hourOfDay,minute);
    }

    @Override
    public void onAttach(Activity activity) {
        onTimeI = (OnTimeI) activity;
        super.onAttach(activity);
    }
}

