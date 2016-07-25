package com.example.jhon.smi_servicios;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Fragments.DateFragment;
import com.example.jhon.smi_servicios.Fragments.TimeFragment;

import org.w3c.dom.Text;

import java.util.Random;

public class DriverServicesActivity extends AppCompatActivity implements View.OnClickListener,TimeFragment.OnTimeI, DateFragment.OnDateSetI {
    TextInputLayout starAdress, finishAdress;
    Button request, timePicker, datePicker;
    Toolbar toolbar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_services);

        starAdress = (TextInputLayout) findViewById(R.id.driver_services_actual_direction);
        finishAdress = (TextInputLayout) findViewById(R.id.driver_services_future_direction);
        request = (Button) findViewById(R.id.driver_services_request_button);
        timePicker = (Button) findViewById(R.id.driver_services_time);
        datePicker = (Button) findViewById(R.id.driver_services_date);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        timePicker.setOnClickListener(this);
        request.setOnClickListener(this);
        datePicker.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.driver_services_date:
                DialogFragment dateFragment = new DateFragment();
                dateFragment.show(getFragmentManager(),"DatePicker");
                break;

            case R.id.driver_services_time:
                DialogFragment timeFragment = new TimeFragment();
                timeFragment.show(getFragmentManager(),"TimePicker");
                Toast.makeText(this,"asfd",Toast.LENGTH_SHORT).show();
                break;

            case R.id.driver_services_request_button:
                builder = new AlertDialog.Builder(this);
                Random random = new Random();
                int code = random.nextInt(1000);
                builder.setTitle(String.valueOf(code));
                builder.setMessage("Guarde su codigo, sera solicitado mas adelante");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                break;

        }

    }

    @Override
    public void onTimeSetInterface(int hour, int minute) {
        Toast.makeText(this,"Hora: " + hour + "Minutois " + minute,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDateSetted(int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this,"año: "+ year +"mont: " + monthOfYear + "day: "+ dayOfMonth,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;



        }
        return super.onOptionsItemSelected(item);
    }


}
