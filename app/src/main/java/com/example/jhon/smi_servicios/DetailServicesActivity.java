package com.example.jhon.smi_servicios;

import android.app.Service;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Adapters.ListServicesAdapter;
import com.example.jhon.smi_servicios.Models.Services;
import com.example.jhon.smi_servicios.Net.ServicesDao;
import com.example.jhon.smi_servicios.Util.Constants;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;



public class DetailServicesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Integer type;
    List<Services> services;
    ListView list;
    TextView title;
    CollapsingToolbarLayout colllapse;
    Toolbar toolbar;
    MobileServiceClient mClient;
    MobileServiceTable<Services> mTable;
    ListServicesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_services);
        toolbar = (Toolbar) findViewById(R.id.detail_services_toolbar);
        colllapse = (CollapsingToolbarLayout) findViewById(R.id.detail_services_collapse);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list = (ListView) findViewById(R.id.activity_detail_list);
        services = new ArrayList<>();


        try {
            mClient = new MobileServiceClient(Constants.AZURE_MOBILE_SERVICE_URL,Constants.AZURE_MOBILE_SERVICE_KEY,this);
            ServicesDao servicesDao = new ServicesDao(mClient);



            type = getIntent().getExtras().getInt(Constants.TYPE_CLIENT_SERVICES);
            if (type == Constants.HOME_SERVICES){
                servicesDao.getHomeAsistenceServices(new ServicesDao.OnDataBaseResponse() {
                    @Override
                    public void onCompletedQuery(int stateResult, MobileServiceList<Services> data) {
                        if (stateResult == ServicesDao.QUERY_COMPLETED){
                            adapter = new ListServicesAdapter(data,getApplicationContext());
                            services.addAll(data);
                            list.setAdapter(adapter);

                        }

                        else {
                            Toast.makeText(getApplicationContext(),"Error obteniedo servicios", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                colllapse.setTitle("Servicios del Hogar");


            }

            else if (type == Constants.ROAD_ASISTENCE_SERVICES){
                colllapse.setTitle("Servicios de Camino");
                servicesDao.getRoadAssitenceServices(new ServicesDao.OnDataBaseResponse() {
                    @Override
                    public void onCompletedQuery(int stateResult, MobileServiceList<Services> data) {
                        if (stateResult == ServicesDao.QUERY_COMPLETED){
                            adapter = new ListServicesAdapter(data,getApplicationContext());
                            list.setAdapter(adapter);
                            services.addAll(data);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error obteniedo servicios", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            list.setOnItemClickListener(this);






        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("AZURE",e.toString());
            Toast.makeText(this,"Error conectando con El servidor",Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"" + position,Toast.LENGTH_SHORT).show();
        if (type == Constants.HOME_SERVICES){

        }

        else if (type == Constants.ROAD_ASISTENCE_SERVICES){

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
