package com.example.jhon.smi_servicios.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by jhon on 8/06/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface OnDateSetI{
        void OnDateSetted (int year, int monthOfYear, int dayOfMonth, String tag);
    }
    String tag;
    OnDateSetI onDateSetI;


    @Override
    public void onAttach(Activity activity) {
        this.onDateSetI = (OnDateSetI) activity;
        super.onAttach(activity);
    }

    public void fragmentInit(String tag){
        this.tag = tag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //Log.i("Fecha",view.getTag().toString());
        if (tag != null) {
            onDateSetI.OnDateSetted(year, monthOfYear, dayOfMonth, tag);
        }
        else {
            onDateSetI.OnDateSetted(year, monthOfYear, dayOfMonth, "Default");
        }
    }
}
