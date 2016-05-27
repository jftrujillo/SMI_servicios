package com.example.jhon.smi_servicios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Adapters.ListServicesAdapter;
import com.example.jhon.smi_servicios.Models.Services;
import com.example.jhon.smi_servicios.Util.Constants;

import java.util.ArrayList;
import java.util.List;


public class DetailServicesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Integer type;
    List<Services> services;
    ListView list;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_services);
        list = (ListView) findViewById(R.id.activity_detail_list);
        title =  (TextView) findViewById(R.id.detail_services_title);
        services = new ArrayList<>();
        type = getIntent().getExtras().getInt(Constants.TYPE_CLIENT_SERVICES);
        if (type == Constants.HOME_SERVICES){
            title.setText("Servicios Hogar");
            services.addAll(Services.getServicesHome());
        }

        else if (type == Constants.ROAD_ASISTENCE_SERVICES){
            services.addAll(Services.getServicesRoadAsistence());
            title.setText("Servicios De Camino");
        }

        ListServicesAdapter adapter = new ListServicesAdapter(services,this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
