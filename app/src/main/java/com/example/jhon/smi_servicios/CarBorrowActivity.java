package com.example.jhon.smi_servicios;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Fragments.DateFragment;
import com.example.jhon.smi_servicios.Models.CarBorrow;
import com.example.jhon.smi_servicios.Net.CarBorrowDao;
import com.example.jhon.smi_servicios.Net.PetitionsResultI;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.StringsValidation;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.Random;

public class CarBorrowActivity extends AppCompatActivity implements View.OnClickListener,DateFragment.OnDateSetI, PetitionsResultI {
    ImageView portada;
    TextInputLayout numeroPuestos,descripcion;
    Button botonSolicitar,fechaInicio,fechaFinal;
    TextView fechaInicioTexto,fechaFinalText;
    SharedPreferences sharedPreferences;
    MobileServiceClient mClient;
    CarBorrowDao carBorrowDao;
    ProgressDialog progress;
    int rnd;
    AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_borrow);
        portada = (ImageView) findViewById(R.id.img);
        numeroPuestos = (TextInputLayout) findViewById(R.id.numero_puestos);
        descripcion = (TextInputLayout) findViewById(R.id.descripcion);
        botonSolicitar = (Button) findViewById(R.id.btn_acept);
        fechaInicio = (Button) findViewById(R.id.car_borrow_date_start);
        fechaFinal = (Button) findViewById(R.id.car_borrow_date_date_finish);
        fechaInicioTexto = (TextView) findViewById(R.id.date_start);
        fechaFinalText = (TextView) findViewById(R.id.date_finish);
        Picasso.with(this).load("http://www.curiosodato.net/wp-content/uploads/2015/11/Carro.jpg").into(portada);
        botonSolicitar.setOnClickListener(this);
        fechaInicio.setOnClickListener(this);
        fechaFinal.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
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
                if(StringsValidation.ValidateString(numeroPuestos.getEditText().getText().toString(),descripcion.getEditText().getText().toString())
                        && StringsValidation.ValidateDates(fechaInicioTexto.getText().toString(),fechaFinal.getText().toString())
                        ){
                    Random random = new Random();
                    rnd = random.nextInt(1000);
                    CarBorrow carBorrow = new CarBorrow();
                    carBorrow.setDatefinish(fechaFinalText.getText().toString());
                    carBorrow.setDatestart(fechaInicioTexto.getText().toString());
                    carBorrow.setDescription(descripcion.getEditText().getText().toString());
                    carBorrow.setNumberspots(numeroPuestos.getEditText().getText().toString());
                    carBorrow.setState(0);
                    carBorrow.setUserid(sharedPreferences.getString(Constants.userID,""));
                    carBorrow.setRandomCode(String.valueOf(rnd));
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
        if (tag.equals("fecha_Start")){
            fechaInicioTexto.setText("" + dayOfMonth + "/"+ monthOfYear + "/" + year);
        }
        if (tag.equals("fecha_final")){
            fechaFinalText.setText("" + dayOfMonth + "/"+ monthOfYear + "/" + year);
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
}
