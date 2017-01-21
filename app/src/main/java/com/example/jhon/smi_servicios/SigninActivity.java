package com.example.jhon.smi_servicios;

import android.app.ActivityManager;
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

import com.example.jhon.smi_servicios.Models.Users;
import com.example.jhon.smi_servicios.Util.Constants;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;


public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout singEmail, singPass, singConfirmPass, singName;
    Button singIn, logIn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    ProgressDialog progress;

    public MobileServiceClient mClient;
    public MobileServiceTable<Users> mTableUSers;
    public MobileServiceList<Users> users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        singEmail = (TextInputLayout) findViewById(R.id.sign_email);
        singPass = (TextInputLayout) findViewById(R.id.sign_password);
        singConfirmPass = (TextInputLayout) findViewById(R.id.sign_confirm_password);
        singName = (TextInputLayout) findViewById(R.id.sign_name);

        singIn = (Button) findViewById(R.id.sign_sign);
        logIn = (Button) findViewById(R.id.sign_login);
        singIn.setOnClickListener(this);
        logIn.setOnClickListener(this);
        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        editor = preferences.edit();

        try {
            mClient = new MobileServiceClient(Constants.url,Constants.key,this);
            mTableUSers = mClient.getTable(Users.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_sign:
                if (singPass.getEditText().getText().toString().equals("") || singEmail.getEditText().getText().toString().equals("") || singName.getEditText().getText().toString().equals("")){
                 Toast.makeText(this,"Valores de email, contraseña o nombre no validos",Toast.LENGTH_SHORT).show();
                }

                else {
                    if (singPass.getEditText().getText().toString().equals(singConfirmPass.getEditText().getText().toString())) {
                        progress= ProgressDialog.show(SigninActivity.this,"Validando Correo","Por favor, espere",true);
                        validateNewUser(singEmail.getEditText().getText().toString(), singPass.getEditText().getText().toString(), singName.getEditText().getText().toString());
                    }

                    else
                    {
                        Toast.makeText(this,"Contraseñas no iguales",Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.sign_login:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            }

        }

    private void validateNewUser(final String email, final String pass, final String name) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    users = mTableUSers.where().field("mail").eq(email).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if (users.size()>0){
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(),"Email ya registrado",Toast.LENGTH_SHORT).show();
                }
                else {
                    progress.dismiss();
                    progress = ProgressDialog.show(SigninActivity.this,"Registrando Usuario","Por favor, espere",true);
                    setNewUser(email,pass,name);
                }
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void setNewUser (final String email, final String pass, final String name) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Users user = new Users();
                user.setType(1);
                user.setMail(email);
                user.setName(name);
                user.setPassword(pass);
                user.setAdress("");
                user.setAge("");
                user.setCellphone("");
                user.setGenre("");
                user.setIdentifycard("");
                user.setTelephone("");
                user.setIsvalid(0);
                    mTableUSers.insert(user, new TableOperationCallback<Users>() {
                        @Override
                        public void onCompleted(Users entity, Exception exception, ServiceFilterResponse response) {

                            if (exception == null){
                                Log.i("Azure","Usuario Creado");
                                editor.putString(Constants.userID,entity.getId());
                                editor.putString(Constants.userEmai,entity.getMail());
                                editor.putBoolean(Constants.isLoged,true);
                                editor.putString(Constants.typeUser,String.valueOf(entity.getType()));
                                editor.putString(Constants.userName,entity.getName());
                                editor.putBoolean(Constants.isComplete,false);
                                editor.commit();
                                progress.dismiss();
                                startActivity(new Intent(getApplicationContext(),ClientServicesActivity.class));
                                finish();
                            }
                            else {
                                Log.i("AZURE","Usuario not created "+ exception.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Problemas Creando el usuario",Toast.LENGTH_SHORT).show();
                                        progress.dismiss();
                                    }
                                });
                            }
                        }
                        }
                    );


                return null;
            }
        }.execute();
    }


       public void progressDismiss (){
           progress.dismiss();

       }
}

