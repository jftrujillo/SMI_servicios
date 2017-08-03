package com.example.jhon.smi_servicios;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Fragments.ProblemOptionsFragment;
import com.example.jhon.smi_servicios.Models.Homepetitions;
import com.example.jhon.smi_servicios.Net.HomePetitionsDao;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.StringsValidation;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Random;



public class HomeServicesActivity extends AppCompatActivity implements View.OnClickListener, HomePetitionsDao.OnDataBaseResponse, ProblemOptionsFragment.IProblemInterface {
    Bundle bundle;
    TextInputLayout direction;
    String descripcion = "";
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
    Toolbar toolbar;
    ProblemOptionsFragment fragmentDescripcion;
    RelativeLayout container;

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


        bundle = getIntent().getExtras();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_black_24dp));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(bundle.getString(Constants.SERVICE_NAME));
        container = (RelativeLayout) findViewById(R.id.container);

        fragmentDescripcion = new ProblemOptionsFragment();
        fragmentDescripcion.initFragment(Constants.getStringArrayFromFragment(bundle.getString(Constants.SERVICE_ID),this),null);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, fragmentDescripcion);
        fragmentTransaction.commit();

        imgService = (ImageView) findViewById(R.id.img);
        titleService = (TextView) findViewById(R.id.title_service);

        urlImg = bundle.getString(Constants.SERVICE_IMG_URL);


        Picasso.with(this).load(urlImg).into(imgService);
        titleService.setVisibility(View.GONE);

        builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.confirmacion));
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            finish();
            }
        });


        direction = (TextInputLayout) findViewById(R.id.direction);
        btn = (Button) findViewById(R.id.btn_acept);

        btn.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (StringsValidation.ValidateString(direction.getEditText().getText().toString(),direction.getEditText().getText().toString(),descripcion)){
            Random random = new Random();
            rnd = random.nextInt(1000);
            Homepetitions homepetitions = new Homepetitions();
            homepetitions.setServicename(bundle.getString(Constants.SERVICE_NAME));
            homepetitions.setAddress(direction.getEditText().getText().toString());
            homepetitions.setDescription(descripcion);
            homepetitions.setRandomcode(String.valueOf(rnd));
            homepetitions.setState(Homepetitions.PETITION_PENDING);
            homepetitions.setUserid(getSharedPreferences(Constants.preferencesName,MODE_PRIVATE).getString(Constants.userID,""));
            homepetitions.setCreado(new Date().getTime());
            progressDialog = ProgressDialog.show(this,"Creando su solicitud",", por favor espere un momento",true,false);
            homePetitionsDao.createNewPetition(homepetitions,this);
        }
        else{
            Toast.makeText(this, "Campos inválidos, por favor revise la información", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onQueryCompleted(int stateResult, MobileServiceList<Homepetitions> data) {
        if (stateResult == HomePetitionsDao.QUERY_COMPLETED){
            builder.setTitle(String.valueOf(rnd));
            dialog = builder.create();
            dialog.show();
            progressDialog.dismiss();
            Toast.makeText(this, "La informaciòn de confirmaciòn llegara a su correo electrònico", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("No se pudo crear su solicitud, verifique su conexión a internet e intentelo nuevamente");
            builder.setPositiveButton("Aceptar", null);
            builder.setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public void OnOptionSetted(String option, String tag) {
        descripcion = option;
    }
}
