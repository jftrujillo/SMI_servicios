package com.example.jhon.smi_servicios;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Fragments.DateFragment;
import com.example.jhon.smi_servicios.Fragments.TimeFragment;
import com.example.jhon.smi_servicios.Models.driverpetitions;
import com.example.jhon.smi_servicios.Net.DriverPetitionsDao;
import com.example.jhon.smi_servicios.Net.PetitionsResultI;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.StringsValidation;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.sql.Date;
import java.util.EventListener;
import java.util.Random;


public class DriverServicesActivity extends AppCompatActivity implements View.OnClickListener,TimeFragment.OnTimeI, DateFragment.OnDateSetI, PetitionsResultI {
    TextInputLayout starAdress, finishAdress;
    Button request, timePicker, datePicker;
    Toolbar toolbar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    TextView date,hour;
    driverpetitions driverPet;
    SharedPreferences preferences;
    Bundle bundle;
    DriverPetitionsDao driverPetitionsDao;
    MobileServiceClient mClient;
    int code;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_services);
        try {
            mClient = new MobileServiceClient(Constants.url,Constants.key,this);
            driverPetitionsDao = new DriverPetitionsDao(mClient,this);
        } catch (MalformedURLException e) {
            Toast.makeText(this,"Error conectando con el servidor",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        starAdress = (TextInputLayout) findViewById(R.id.driver_services_actual_direction);
        finishAdress = (TextInputLayout) findViewById(R.id.driver_services_future_direction);
        request = (Button) findViewById(R.id.driver_services_request_button);
        timePicker = (Button) findViewById(R.id.driver_services_time);
        datePicker = (Button) findViewById(R.id.driver_services_date);
        bundle = getIntent().getExtras();
        date = (TextView) findViewById(R.id.date);

        hour = (TextView) findViewById(R.id.hour);
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
                if (StringsValidation.ValidateString(starAdress.getEditText().getText().toString(),finishAdress.getEditText().getText().toString(),date.getText().toString(),hour.getText().toString())){
                    progressDialog = ProgressDialog.show(this,"Creando solicitud","Por favor espere",true,false);
                    driverPet = new driverpetitions();
                    driverPet.setUserid(preferences.getString(Constants.userID,""));
                    driverPet.setState(driverpetitions.PETITION_PENDING);
                    driverPet.setActualaddress(starAdress.getEditText().getText().toString());
                    driverPet.setFutureaddress(finishAdress.getEditText().getText().toString());
                    driverPet.setServicename(bundle.getString(Constants.SERVICE_NAME,""));
                    driverPet.setDate(date.getText().toString());
                    driverPet.setTime(hour.getText().toString());
                    driverPet.setInsuranceid("");
                    Random random = new Random();
                    code = random.nextInt(1000);
                    driverPet.setCode(String.valueOf(code));
                    driverPetitionsDao.createNewDriverPetition(driverPet);
                }
                else {
                    Toast.makeText(this, "Campos incorrectos, por favor verifique el formulario", Toast.LENGTH_SHORT).show();
                }
                
                break;

        }

    }

    @Override
    public void onTimeSetInterface(int hour, int minute) {
        Toast.makeText(this,"Hora: " + hour + "Minutois " + minute,Toast.LENGTH_SHORT).show();
        this.hour.setText(""+hour+":"+minute);

    }

    @Override
    public void OnDateSetted(int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this,"año: "+ year +"mont: " + monthOfYear + "day: "+ dayOfMonth,Toast.LENGTH_SHORT).show();
        this.date.setText(""+year+"/"+monthOfYear+"/"+dayOfMonth);
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


    @Override
    public void OnInsertFinished(int state, String error) {
        if (state == DriverPetitionsDao.INSERT_CORRECT){
            progressDialog.dismiss();
            builder = new AlertDialog.Builder(this);
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
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(this,"Fallo la creacion de la soliciut",Toast.LENGTH_SHORT).show();
        }
    }
}
