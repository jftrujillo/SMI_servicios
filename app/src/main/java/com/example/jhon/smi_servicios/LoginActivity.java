package com.example.jhon.smi_servicios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jhon.smi_servicios.Models.Users;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.GetUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout email, password;
    Button sing_in, log_in;
    SharedPreferences preferences;
    SharedPreferences.Editor editorPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        editorPreferences = preferences.edit();

        email =(TextInputLayout) findViewById(R.id.login_email);
        password = (TextInputLayout) findViewById(R.id.login_password);
        sing_in = (Button) findViewById(R.id.login_sigin);
        log_in = (Button) findViewById(R.id.login_login);

        sing_in.setOnClickListener(this);
        log_in.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_sigin:
                GetUser userfacade= new GetUser(email.getEditText().getText().toString(),password.getEditText().getText().toString());
                Users user = userfacade.LoginUser();
                if (user != null){
                    editorPreferences.putBoolean(Constants.isLoged,true);
                    editorPreferences.putString(Constants.userEmai,user.getMail());
                    editorPreferences.putString(Constants.userID, String.valueOf(user.getId()));
                    editorPreferences.putString(Constants.typeUser,String.valueOf(user.getType()));
                    editorPreferences.commit();

                    if (user.getType() == Constants.CLIENT){
                    startActivity(new Intent(this,ClientServicesActivity.class));
                    }

                    if (user.getType() == Constants.ADMIN){
                        startActivity(new Intent(this,AdminServiciesActivity.class));
                    }


                }

                else {
                    Toast.makeText(this,"Usuario no encontrado",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.login_login:


                break;


        }
    }
}
