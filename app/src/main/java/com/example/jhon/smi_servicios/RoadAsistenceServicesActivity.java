package com.example.jhon.smi_servicios;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Models.Roadpetitions;
import com.example.jhon.smi_servicios.Net.PetitionsResultI;
import com.example.jhon.smi_servicios.Net.RoadPetitionsDAO;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.StringsValidation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;
import java.util.Random;
import java.util.jar.*;


public class RoadAsistenceServicesActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, PetitionsResultI {
    public GoogleMap map;
    Button request;
    EditText description;
    Toolbar toolbar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    TextInputLayout cartype, carline, placa;
    Bundle bundle;
    double latitude, longitude;
    SharedPreferences preferences;
    MobileServiceClient mClient;
    RoadPetitionsDAO roadPetitionsDAO;
    int code;
    ProgressDialog progressMap;
    private static final int MY_PERMISION = 112;
    LocationManager lm;
    LocationListener locationListener;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_asistence_services);

        try {
            mClient = new MobileServiceClient("https://smiserviciosmovil.azure-mobile.net/",
                    "qIufyUhXNGYkLUXenUUDufQFPMdcUm65",
                    this);
            roadPetitionsDAO = new RoadPetitionsDAO(mClient, this);

        } catch (MalformedURLException e) {
            Toast.makeText(this, "Falló conexión con servidor", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }

        request = (Button) findViewById(R.id.road_asistence_button);
        request.setOnClickListener(this);
        description = (EditText) findViewById(R.id.road_asistence_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartype = (TextInputLayout) findViewById(R.id.cartype);
        carline = (TextInputLayout) findViewById(R.id.carline);
        placa = (TextInputLayout) findViewById(R.id.placa);
        bundle = getIntent().getExtras();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Asistencia Vial");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                progressMap.dismiss();
                lm.removeUpdates(locationListener);
                if (location != null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(RoadAsistenceServicesActivity.this);
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RoadAsistenceServicesActivity.this);
                    builder.setTitle("Advertencia");
                    builder.setMessage("Problema para obtener su posición, por favor verifique su conexión y habilite el gps de su celular");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(RoadAsistenceServicesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(RoadAsistenceServicesActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            progressMap = ProgressDialog.show(this, "Obteniendo ubicación", "un momento por favor", true, true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            ActivityCompat.requestPermissions(RoadAsistenceServicesActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        },MY_PERMISION);

    }

        @Override
        public void onClick(View v) {
            if (StringsValidation.ValidateString(carline.getEditText().getText().toString(),cartype.getEditText().getText().toString(),description.getText().toString(),placa.getEditText().getText().toString())){
                progressDialog = ProgressDialog.show(this,"Creando su solicitud","Por favor espere",true,false);
                String descriptionFromRequest = description.getText().toString();
                Roadpetitions roadpetitions = new Roadpetitions();
                roadpetitions.setCarline(carline.getEditText().getText().toString());
                roadpetitions.setCartype(cartype.getEditText().getText().toString());
                roadpetitions.setDescription(description.getText().toString());
                roadpetitions.setPlaca(placa.getEditText().getText().toString());
                Toast.makeText(this, descriptionFromRequest,Toast.LENGTH_SHORT).show();
                builder = new AlertDialog.Builder(this);
                Random random = new Random();
                code = random.nextInt(1000);
                roadpetitions.setRandomcode(String.valueOf(code));
                roadpetitions.setServicename(bundle.getString(Constants.SERVICE_NAME));
                roadpetitions.setState(Roadpetitions.PETITION_PENDING);
                roadpetitions.setLatitude(String.valueOf(latitude));
                roadpetitions.setLongitude(String.valueOf(longitude));
                roadpetitions.setInsuranceid("");
                roadpetitions.setUserid(preferences.getString(Constants.userID,""));
                roadPetitionsDAO.createNewRoadPetition(roadpetitions);
            }
            else {
                Toast.makeText(this, "Campos inválido, por favor revise la información en el formulario", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                progressMap = ProgressDialog.show(this, "Obteniendo ubicación", "un momento por favor", true, true, new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        });
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
        else {
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng pop = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(pop).title("Su posicion"));
        map.getMaxZoomLevel();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pop,15));
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
        progressDialog.dismiss();
        switch (state){
            case RoadPetitionsDAO.INSERT_CORRECT:
                builder.setTitle(String.valueOf(code));
                builder.setMessage("Guarde su código, sera solicitado más adelante");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                break;

            case RoadPetitionsDAO.INSERT_FAILED:
                Toast.makeText(this,"Falló el crear la nueva petición",Toast.LENGTH_LONG).show();
                break;

        }


    }
}
