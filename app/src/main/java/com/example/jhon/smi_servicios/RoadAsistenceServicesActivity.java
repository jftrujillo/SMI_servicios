package com.example.jhon.smi_servicios;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;


public class RoadAsistenceServicesActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    public GoogleMap map;
    Button request;
    EditText description;
    Toolbar toolbar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_asistence_services);

        request = (Button) findViewById(R.id.road_asistence_button);
        description = (EditText) findViewById(R.id.road_asistence_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

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
            String descriptionFromRequest = description.getText().toString();
            Toast.makeText(this, descriptionFromRequest,Toast.LENGTH_SHORT).show();
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
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pop = new LatLng(2.450471768487052, -76.61068232205548);
        map.addMarker(new MarkerOptions().position(pop).title("Su posicion"));
        map.moveCamera(CameraUpdateFactory.newLatLng(pop));
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
