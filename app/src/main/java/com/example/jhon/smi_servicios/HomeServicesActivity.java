package com.example.jhon.smi_servicios;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Models.Homepetitions;
import com.example.jhon.smi_servicios.Net.HomePetitionsDao;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.StringsValidation;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.Random;



public class HomeServicesActivity extends AppCompatActivity implements View.OnClickListener, HomePetitionsDao.OnDataBaseResponse {
    Bundle bundle;
    TextInputLayout description, direction;
    ImageView imgService;
    TextView titleService;
    Button btn;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    String urlImg;
    String serviceName;
    MobileServiceClient mClient;
    HomePetitionsDao homePetitionsDao;
    int rnd;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_services);

        try {
            mClient = new MobileServiceClient(Constants.url,Constants.key,this);
            homePetitionsDao = new HomePetitionsDao(mClient);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
        if (StringsValidation.ValidateString(direction.getEditText().getText().toString(),direction.getEditText().getText().toString(),description.getEditText().getText().toString())){
            Random random = new Random();
            rnd = random.nextInt(1000);
            Homepetitions homepetitions = new Homepetitions();
            homepetitions.setServicename(bundle.getString(Constants.SERVICE_NAME));
            homepetitions.setAddress(direction.getEditText().getText().toString());
            homepetitions.setDescription(description.getEditText().getText().toString());
            homepetitions.setRandomcode(String.valueOf(rnd));
            homepetitions.setState(Homepetitions.PETITION_PENDING);
            homepetitions.setUserid(getSharedPreferences(Constants.preferencesName,MODE_PRIVATE).getString(Constants.userID,""));
            progressDialog = ProgressDialog.show(this,"Creando su solicitud",", por favor espere un momento",true,false);
            homePetitionsDao.createNewPetition(homepetitions,this);
        }
        else{
            Toast.makeText(this, "Campos invalidos, por favor revise la informacion", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onQueryCompleted(int stateResult, MobileServiceList<Homepetitions> data) {
        if (stateResult == HomePetitionsDao.QUERY_COMPLETED){
            builder.setTitle(String.valueOf(rnd));
            dialog = builder.create();
            dialog.show();
            progressDialog.dismiss();
        }
        else {
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("No se pudo crear su solicitud, verifique su conexion a internet e intentelo nuevamente");
            builder.setPositiveButton("Aceptar", null);
            builder.setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
