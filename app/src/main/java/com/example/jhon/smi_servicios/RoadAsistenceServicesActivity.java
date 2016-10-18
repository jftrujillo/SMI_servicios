package com.example.jhon.smi_servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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



public class RoadAsistenceServicesActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, PetitionsResultI {
    public GoogleMap map;
    Button request;
    EditText description;
    Toolbar toolbar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    TextInputLayout cartype, carline;
    Bundle bundle;
    double latitude,longitude;
    SharedPreferences preferences;
    MobileServiceClient mClient;
    RoadPetitionsDAO roadPetitionsDAO;
    int code;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_asistence_services);

        try {
            mClient = new MobileServiceClient("https://smiserviciosmovil.azure-mobile.net/",
                    "qIufyUhXNGYkLUXenUUDufQFPMdcUm65",
                    this);
             roadPetitionsDAO = new RoadPetitionsDAO(mClient,this);

        } catch (MalformedURLException e) {
            Toast.makeText(this,"Fallo conexion con servidor",Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }


        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        request = (Button) findViewById(R.id.road_asistence_button);
        description = (EditText) findViewById(R.id.road_asistence_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartype = (TextInputLayout) findViewById(R.id.cartype);
        carline = (TextInputLayout) findViewById(R.id.carline);

        bundle = getIntent().getExtras();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Asistencia Vial");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        request.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            if (StringsValidation.ValidateString(carline.getEditText().getText().toString(),cartype.getEditText().getText().toString(),description.getText().toString())){
                progressDialog = ProgressDialog.show(this,"Creando su solicitud","Por favor espere",true,false);
                String descriptionFromRequest = description.getText().toString();
                Roadpetitions roadpetitions = new Roadpetitions();
                roadpetitions.setCarline(carline.getEditText().getText().toString());
                roadpetitions.setCartype(cartype.getEditText().getText().toString());
                roadpetitions.setDescription(description.getText().toString());
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
                Toast.makeText(this, "Campos invalido, por favor revise la informacion en el formulario", Toast.LENGTH_SHORT).show();
            }

        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng pop = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(pop).title("Su posicion"));
        map.getMaxZoomLevel();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pop,50));

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

            case RoadPetitionsDAO.INSERT_FAILED:
                Toast.makeText(this,"Fallo el crear la nueva peticion",Toast.LENGTH_LONG).show();
                break;

        }


    }
}
