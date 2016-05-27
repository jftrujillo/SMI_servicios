package com.example.jhon.smi_servicios;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DriverServicesActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout date,starAdress, finishAdress;
    Button request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_services);

        date = (TextInputLayout) findViewById(R.id.driver_services_date);
        starAdress = (TextInputLayout) findViewById(R.id.driver_services_actual_direction);
        finishAdress = (TextInputLayout) findViewById(R.id.driver_services_future_direction);
        request = (Button) findViewById(R.id.driver_services_request_button);

        request.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,date.getEditText().getText().toString()+starAdress.getEditText().getText().toString(),Toast.LENGTH_SHORT).show();
    }
}
