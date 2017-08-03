package com.example.jhon.smi_servicios;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jhon.smi_servicios.Models.Users;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.GetUser;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.net.MalformedURLException;



public class ClientServicesActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout home,driver,roadassitence,carBorrow;
    Toolbar toobar;
    CollapsingToolbarLayout collapse;
    ImageView icon1;
    String cupon;
    SharedPreferences preferences;
    SharedPreferences.Editor editorPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_services);
        toobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapse = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapse.setTitle("Servicios");
        collapse.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        collapse.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));
        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        cupon = preferences.getString(Constants.CODIGO_PROM,null);




        home = (LinearLayout) findViewById(R.id.client_services_home);
        driver= (LinearLayout) findViewById(R.id.client_services_driver);
        roadassitence= (LinearLayout) findViewById(R.id.client_services_roadasistence);
        icon1 = (ImageView) findViewById(R.id.icon1_client_services);
        carBorrow = (LinearLayout) findViewById(R.id.client_services_borrow_car);
        carBorrow.setOnClickListener(this);
        home.setOnClickListener(this);
        driver.setOnClickListener(this);
        roadassitence.setOnClickListener(this);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_client,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cupon:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle(cupon != null ? "Su código de promoción es: " + cupon : "No cuenta con un código de promoción");
                            builder.setCancelable(true);
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //
                                }
                            });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.cerrar_session:
                editorPreferences = preferences.edit();
                editorPreferences.putString(Constants.userEmai, "");
                editorPreferences.putString(Constants.userID, "");
                editorPreferences.putString(Constants.typeUser, "");
                editorPreferences.putString(Constants.password,"");
                editorPreferences.putString(Constants.CODIGO_PROM,"");
                editorPreferences.putBoolean(Constants.isLoged,false);
                editorPreferences.commit();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.client_services_driver:
                startActivity(new Intent(this, DriverServicesActivity.class).putExtra(Constants.TYPE_CLIENT_SERVICES,Constants.DRIVER_SERVICES));
                break;
            case R.id.client_services_home:
                startActivity(new Intent(this,DetailServicesActivity.class).putExtra(Constants.TYPE_CLIENT_SERVICES,Constants.HOME_SERVICES));
                break;
            case R.id.client_services_roadasistence:
                startActivity(new Intent(this,DetailServicesActivity.class).putExtra(Constants.TYPE_CLIENT_SERVICES, Constants.ROAD_ASISTENCE_SERVICES));
                break;
            case R.id.client_services_borrow_car:
                startActivity(new Intent(ClientServicesActivity.this,CarBorrowActivity.class));
                break;
        }

    }
}
