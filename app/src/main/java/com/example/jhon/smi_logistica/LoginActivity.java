package com.example.jhon.smi_logistica;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jhon.smi_logistica.Models.Users;
import com.example.jhon.smi_logistica.Util.Constants;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //region Variables y objetos
    TextInputLayout email, password;
    Button sing_in, log_in;
    SharedPreferences preferences;
    SharedPreferences.Editor editorPreferences;
    public MobileServiceClient mClient;
    public MobileServiceTable<Users> mTableUsers;
    public MobileServiceList<Users> mListUsers;
    ProgressDialog progress;


    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        editorPreferences = preferences.edit();

        //region views and listeners
        email =(TextInputLayout) findViewById(R.id.login_email);
        password = (TextInputLayout) findViewById(R.id.login_password);
        sing_in = (Button) findViewById(R.id.login_sigin);
        log_in = (Button) findViewById(R.id.login_login);

        sing_in.setOnClickListener(this);
        log_in.setOnClickListener(this);

        //endregion


        //region conection with azure database

        try {
            mClient = new MobileServiceClient("https://smiserviciosmovil.azure-mobile.net/",
                    "qIufyUhXNGYkLUXenUUDufQFPMdcUm65",
                    this);

            mTableUsers = mClient.getTable(Users.class);

        } catch (MalformedURLException e) {
            Toast.makeText(this,"No fue posible conectar con la base de datos",Toast.LENGTH_SHORT).show();
        }

        //endregion




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_sigin:
                    startActivity(new Intent(this,SigninActivity.class));
                    finish();


                break;

            case R.id.login_login:
                progress = ProgressDialog.show(this,getString(R.string.startin_session),getString(R.string.please_wait),true);
                getLogin(email.getEditText().getText().toString(), password.getEditText().getText().toString());
                break;


        }
    }


    public void getLogin(final String mail, final String pass){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mListUsers = mTableUsers.where().field("mail").eq(mail).and().field("password").eq(pass).execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mListUsers.size() > 0) {
                                if (mListUsers.get(0).isvalid() != 2) {
                                    editorPreferences.putBoolean(Constants.isLoged, true);
                                    if (mListUsers.get(0).isvalid() == 0) {
                                        editorPreferences.putBoolean(Constants.isComplete, false);
                                    } else if (mListUsers.get(0).isvalid() == 1) {
                                        editorPreferences.putBoolean(Constants.isComplete, true);
                                    }

                                    editorPreferences.putString(Constants.userEmai, mListUsers.get(0).getMail());
                                    editorPreferences.putString(Constants.userID, String.valueOf(mListUsers.get(0).getId()));
                                    editorPreferences.putString(Constants.typeUser, String.valueOf(mListUsers.get(0).getType()));
                                    editorPreferences.putString(Constants.password,mListUsers.get(0).getPassword());
                                    editorPreferences.putString(Constants.CODIGO_PROM,mListUsers.get(0).getPromocion());


                                    if (mListUsers.get(0).getType() == Constants.CLIENT) {
                                        progress.dismiss();
                                        if ((mListUsers.get(0).isvalid() == 0 || mListUsers.get(0).isvalid() == 1) && mListUsers.get(0).isPerfilcompletado()){
                                            editorPreferences.putBoolean(Constants.isComplete,true);
                                            startActivity(new Intent(LoginActivity.this,ClientServicesActivity.class));
                                        }
                                        else if ((mListUsers.get(0).isvalid() == 0 || mListUsers.get(0).isvalid() == 1) && !mListUsers.get(0).isPerfilcompletado()){
                                            editorPreferences.putBoolean(Constants.isComplete,false);
                                            startActivity(new Intent(getApplicationContext(), CompleteperfilActivity.class));
                                        }
                                        editorPreferences.commit();
                                        finish();
                                    }

                                    if (mListUsers.get(0).getType() == Constants.ADMIN) {
                                        progress.dismiss();
                                        startActivity(new Intent(getApplicationContext(), AdminServiciesActivity.class));
                                        finish();
                                    }

                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Este usuario no es válido.", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                            }

                            else {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(),"Usuario no encontrado",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } catch (Exception exception) {
                    progress.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),getString(R.string.check_internet),Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.i("AZURE EX",""+exception.toString()+"Error");
                }
                return null;
            }

        }.execute();

    }
}
