package com.example.jhon.smi_logistica;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.smi_logistica.Fragments.DateFragment;
import com.example.jhon.smi_logistica.Models.CarBorrow;
import com.example.jhon.smi_logistica.Net.CarBorrowDao;
import com.example.jhon.smi_logistica.Net.PetitionsResultI;
import com.example.jhon.smi_logistica.Util.Constants;
import com.example.jhon.smi_logistica.Util.StringsValidation;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class CarBorrowActivity extends AppCompatActivity implements View.OnClickListener,DateFragment.OnDateSetI, PetitionsResultI {
    ImageView portada;
    TextInputLayout descripcion;
    Button botonSolicitar,fechaInicio,fechaFinal;
    TextView fechaInicioTexto,fechaFinalText;
    SharedPreferences sharedPreferences;
    MobileServiceClient mClient;
    CarBorrowDao carBorrowDao;
    ProgressDialog progress;
    int rnd;
    AlertDialog alertDialog;
    DateFormat formatFecha;
    Toolbar toobar;
    Spinner spniner;



    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_borrow);
        formatFecha = new SimpleDateFormat("yyyy-MMM-dd");
        toobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Alquiler de vehículos");
        portada = (ImageView) findViewById(R.id.img);
        descripcion = (TextInputLayout) findViewById(R.id.descripcion);
        botonSolicitar = (Button) findViewById(R.id.btn_acept);
        fechaInicio = (Button) findViewById(R.id.car_borrow_date_start);
        fechaFinal = (Button) findViewById(R.id.car_borrow_date_date_finish);
        fechaInicioTexto = (TextView) findViewById(R.id.date_start);
        fechaInicioTexto.setText(formatFecha.format(new Date()));
        fechaFinalText = (TextView) findViewById(R.id.date_finish);
        Picasso.with(this).load("http://www.curiosodato.net/wp-content/uploads/2015/11/Carro.jpg").into(portada);
        botonSolicitar.setOnClickListener(this);
        fechaInicio.setOnClickListener(this);
        fechaFinal.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        spniner = (Spinner) findViewById(R.id.spinner_puestos);
        ArrayAdapter<CharSequence> arrayAdapter =  ArrayAdapter.createFromResource(this,R.array.numero_puestos,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spniner.setAdapter(arrayAdapter);



        try {
            mClient = new MobileServiceClient(Constants.url,Constants.key,this);
            carBorrowDao = new CarBorrowDao(mClient,this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.car_borrow_date_start:
                DateFragment dateFragment = new DateFragment();
                dateFragment.fragmentInit("fecha_Start");
                dateFragment.show(getFragmentManager(),"fecha_Start");
                break;
            case R.id.car_borrow_date_date_finish:
                DateFragment dateFragment_2 = new DateFragment();
                dateFragment_2.fragmentInit("fecha_final");
                dateFragment_2.show(getFragmentManager(),"fecha_final");
                break;
            case R.id.btn_acept:
                if(StringsValidation.ValidateString(spniner.getSelectedItem().toString(),descripcion.getEditText().getText().toString())
                        && StringsValidation.ValidateDates(this,fechaInicioTexto.getText().toString(),fechaFinalText.getText().toString())
                        ){
                    Random random = new Random();
                    rnd = random.nextInt(1000);
                    CarBorrow carBorrow = new CarBorrow();
                    carBorrow.setDatefinish(fechaFinalText.getText().toString());
                    carBorrow.setDatestart(fechaInicioTexto.getText().toString());
                    carBorrow.setDescription(descripcion.getEditText().getText().toString());
                    carBorrow.setNumberspots(spniner.getSelectedItem().toString());
                    carBorrow.setState(0);
                    carBorrow.setUserid(sharedPreferences.getString(Constants.userID,""));
                    carBorrow.setRandomCode(String.valueOf(rnd));
                    carBorrow.setCreado(new Date().getTime());
                    carBorrowDao.createNewCarBorrowPetition(carBorrow);
                    progress = ProgressDialog.show(this,"Creando Solicitud","espere por favor",true,false);

                }
                else {
                    Toast.makeText(this, "Campos Inválidos, por favor complete el formulario", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void OnDateSetted(int year, int monthOfYear, int dayOfMonth, String tag) {
        Calendar calendar = new GregorianCalendar();
        if (tag.equals("fecha_Start")){
            calendar.set(year,monthOfYear,dayOfMonth);
            Date dateStart = calendar.getTime();
            if (Calendar.getInstance().before(calendar)){
                fechaInicioTexto.setText(formatFecha.format(dateStart));
                if (!fechaFinalText.getText().equals(getString(R.string.fecha_no_seleccionada))) {
                    Calendar calendarFinal = new GregorianCalendar();
                    try {
                        calendarFinal.setTime(formatFecha.parse(fechaFinalText.getText().toString()));
                        if (calendarFinal.before(calendar)) {
                            fechaFinalText.setText(getString(R.string.fecha_no_seleccionada));
                            Toast.makeText(this, "Por favor, cambie la fecha final", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Toast.makeText(this, "La fecha de inicio debe ser posterior a la fecha actual", Toast.LENGTH_SHORT).show();
            }
        }
        if (tag.equals("fecha_final")){
            calendar.set(year,monthOfYear,dayOfMonth);
            Date dateFinal = calendar.getTime();
            Calendar calendarFechaInicio = new GregorianCalendar();
            try {
                calendarFechaInicio.setTime(formatFecha.parse(fechaInicioTexto.getText().toString()));
                if (calendar.after(calendarFechaInicio)) {
                    fechaFinalText.setText(formatFecha.format(dateFinal));
                } 
                else {
                    Toast.makeText(this, "La fecha final debe ser posterior a la fecha de inicio", Toast.LENGTH_SHORT).show();    
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
        }
    }

    @Override
    public void OnInsertFinished(int state, String error) {
        progress.dismiss();
        if (state == carBorrowDao.INSERT_CORRECT){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.valueOf(rnd));
            builder.setMessage(R.string.confirmacion);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
            Toast.makeText(this, "Se creó la solicitud correctamente", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "Error al crear la solicitud, intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return  true;
    }
}
