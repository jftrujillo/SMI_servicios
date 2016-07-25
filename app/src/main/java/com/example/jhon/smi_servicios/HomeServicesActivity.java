package com.example.jhon.smi_servicios;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jhon.smi_servicios.Util.Constants;
import com.squareup.picasso.Picasso;

import java.util.Random;


public class HomeServicesActivity extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    TextInputLayout description, direction;
    ImageView imgService;
    TextView titleService;
    Button btn;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    String urlImg;
    String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_services);

        imgService = (ImageView) findViewById(R.id.img);
        titleService = (TextView) findViewById(R.id.title_service);

        bundle = getIntent().getExtras();

        urlImg = bundle.getString(Constants.SERVICE_IMG_URL);
        serviceName = bundle.getString(Constants.SERVICE_NAME);

        Picasso.with(this).load(urlImg).into(imgService);
        titleService.setText(serviceName);

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Por favor guarde su codigo de confimacion, sera solicitado mas adelante");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            finish();
            }
        });


        description = (TextInputLayout) findViewById(R.id.description);
        direction = (TextInputLayout) findViewById(R.id.direction);
        btn = (Button) findViewById(R.id.btn_acept);

        btn.setOnClickListener(this);





    }

    @Override
    public void onClick(View view) {
        Random random = new Random();
        int rnd = random.nextInt(1000);
        builder.setTitle(String.valueOf(rnd));
        dialog = builder.create();
        dialog.show();
    }
}
