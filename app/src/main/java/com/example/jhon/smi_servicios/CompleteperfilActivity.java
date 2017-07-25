package com.example.jhon.smi_servicios;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Models.Users;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.StringsValidation;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableDeleteCallback;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.Random;

public class CompleteperfilActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextInputLayout  direccion,edad,numeroIdentificacion,telefono,celular;
    Spinner genero;
    Button bnt;
    public MobileServiceClient mClient;
    public MobileServiceTable<Users> mTableUSers;
    public MobileServiceList<Users> users;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String generoString = " ";
    ProgressDialog progress;
    AlertDialog alert;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completeperfil);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Completar perfil");
        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        editor = preferences.edit();
        direccion = (TextInputLayout) findViewById(R.id.direccion);
        edad = (TextInputLayout) findViewById(R.id.edad);
        numeroIdentificacion= (TextInputLayout) findViewById(R.id.numero_identificacion);
        telefono = (TextInputLayout) findViewById(R.id.telefono);
        celular = (TextInputLayout) findViewById(R.id.celular);
        genero = (Spinner) findViewById(R.id.genero);
        ArrayAdapter<CharSequence> adapterSpinner =  ArrayAdapter.createFromResource(this,R.array.generos,android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adapterSpinner);
        genero.setOnItemSelectedListener(this);
        bnt = (Button) findViewById(R.id.btn);
        bnt.setOnClickListener(this);

        try {
            mClient = new MobileServiceClient(Constants.url,Constants.key,this);
            mTableUSers = mClient.getTable(Users.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Completa tu perfil");
        builder.setMessage("Para nosotros es muy importante conocer esta información acerca de ti, por favor complétala para continuar");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", null);
        alert = builder.create();
        alert.show();

    }

    @Override
    public void onClick(View view) {

        if (StringsValidation.ValidateString(
                direccion.getEditText().getText().toString(),
                edad.getEditText().getText().toString(),
                numeroIdentificacion.getEditText().getText().toString(),
                telefono.getEditText().getText().toString(),
                celular.getEditText().getText().toString(),
                generoString
        )){
            progress =  ProgressDialog.show(this,"Actualizando informacion de usuario","Por favor espere",true,false);
            progress.show();
            updateUser(
                    direccion.getEditText().getText().toString(),
                    edad.getEditText().getText().toString(),
                    numeroIdentificacion.getEditText().getText().toString(),
                    celular.getEditText().getText().toString(),
                    telefono.getEditText().getText().toString(),
                    generoString);
        }
        else {
            Toast.makeText(this, "Campos inválidos o vacios, por favor complete la información", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUser(final String direccion,final String edad,final String identificacion,final String celular, final String telefono, final String genero){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Random random = new Random();
                int promocion = random.nextInt(1000000000);
                final Users user = new Users();
                user.setIsvalid(0);
                user.setAdress(direccion);
                user.setAge(edad);
                user.setCellphone(celular);
                user.setTelephone(telefono);
                user.setGenre(generoString);
                user.setIdentifycard(identificacion);
                user.setId(preferences.getString(Constants.userID, null));
                user.setName(preferences.getString(Constants.userName,null));
                user.setMail(preferences.getString(Constants.userEmai,null));
                user.setType(1);
                user.setPerfilcompletado(true);
                user.setPromocion(String.valueOf(promocion));
                user.setPassword(preferences.getString(Constants.password,""));
                mTableUSers.update(user, new TableOperationCallback<Users>() {
                    @Override
                    public void onCompleted(Users entity, Exception exception, ServiceFilterResponse response) {
                        if (exception == null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CompleteperfilActivity.this);
                                                builder.setTitle("Codigo promoción: " + user.getPromocion());
                                                builder.setMessage("Guarde este codigo para promociones posteriormente");
                                                builder.setCancelable(false);
                                                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        //
                                                        editor.putBoolean(Constants.isComplete,true);
                                                        editor.putString(Constants.CODIGO_PROM,user.getPromocion());
                                                        editor.commit();
                                                        startActivity(new Intent(CompleteperfilActivity.this,ClientServicesActivity.class));
                                                        finish();
                                                    }
                                                });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });

                        }
                        else {
                            Log.i("AZURE","Usuario not created "+ exception.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Problemas Creando usuario",Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                            });
                        }
                    }
                });
                return null;
            }
        }.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        generoString = getResources().getStringArray(R.array.generos)[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}