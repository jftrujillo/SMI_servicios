package com.example.jhon.smi_servicios;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;



public class DetailServicesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Integer type;
    List<Services> services;
    ListView list;
    TextView title;
    ImageView img;
    CollapsingToolbarLayout colllapse;
    Toolbar toolbar;
    MobileServiceClient mClient;
    MobileServiceTable<Services> mTable;
    ListServicesAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_services);
        toolbar = (Toolbar) findViewById(R.id.detail_services_toolbar);
        colllapse = (CollapsingToolbarLayout) findViewById(R.id.detail_services_collapse);
        colllapse.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        colllapse.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = ProgressDialog.show(this,"Recuperando lista de servicios","Un momento por favor",true);

        list = (ListView) findViewById(R.id.activity_detail_list);
        img  = (ImageView) findViewById(R.id.detail_services_image_collapse);
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
                            progressDialog.dismiss();
                        }

                        else {
                            Toast.makeText(getApplicationContext(),"Error obteniedo servicios", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
                colllapse.setTitle("Hogar");
                Picasso.with(this).load(R.drawable.asistencia_hogar).into(img);
            }

            else if (type == Constants.ROAD_ASISTENCE_SERVICES){
                colllapse.setTitle("Ayuda vial");
                Picasso.with(this).load(R.drawable.asistencia_vial).into(img);
                servicesDao.getRoadAssitenceServices(new ServicesDao.OnDataBaseResponse() {
                    @Override
                    public void onCompletedQuery(int stateResult, MobileServiceList<Services> data) {
                        if (stateResult == ServicesDao.QUERY_COMPLETED){
                            adapter = new ListServicesAdapter(data,getApplicationContext());
                            list.setAdapter(adapter);
                            services.addAll(data);
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error obteniedo servicios", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
            list.setOnItemClickListener(this);






        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("AZURE",e.toString());
            Toast.makeText(this,"Error conectando con El servidor",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            finish();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"" + position,Toast.LENGTH_SHORT).show();
        if (type == Constants.HOME_SERVICES){
            Intent intent = new Intent(this,HomeServicesActivity.class);
            intent.putExtra(Constants.SERVICE_ID,services.get(position).getId());
            intent.putExtra(Constants.SERVICE_NAME,services.get(position).getName());
            intent.putExtra(Constants.SERVICE_IMG_URL,services.get(position).getImgurl());
            startActivity(intent);

        }

        else if (type == Constants.ROAD_ASISTENCE_SERVICES){
            Intent intent = new Intent(this,RoadAsistenceServicesActivity.class);
            intent.putExtra(Constants.SERVICE_ID,services.get(position).getId());
            intent.putExtra(Constants.SERVICE_NAME,services.get(position).getName());
            startActivity(intent);
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
