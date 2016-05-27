package com.example.jhon.smi_servicios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jhon.smi_servicios.Models.Users;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.GetUser;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;

import java.net.MalformedURLException;



public class ClientServicesActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout home,driver,roadassitence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_services);


        home = (LinearLayout) findViewById(R.id.client_services_home);
        driver= (LinearLayout) findViewById(R.id.client_services_driver);
        roadassitence= (LinearLayout) findViewById(R.id.client_services_roadasistence);
        home.setOnClickListener(this);
        driver.setOnClickListener(this);
        roadassitence.setOnClickListener(this);






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
                startActivity(new Intent(this,RoadAsistenceServicesActivity.class).putExtra(Constants.TYPE_CLIENT_SERVICES, Constants.ROAD_ASISTENCE_SERVICES));
                break;

        }

    }
}
