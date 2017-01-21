package com.example.jhon.smi_servicios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jhon.smi_servicios.Util.Constants;

public class PivotActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(Constants.preferencesName,MODE_PRIVATE);
        boolean isLoged = preferences.getBoolean(Constants.isLoged,false);
        String type = preferences.getString(Constants.typeUser,"");
        boolean isComplete = preferences.getBoolean(Constants.isComplete,true);

        if (isLoged){
            if (type.equals("1")){
                if (isComplete) {
                    startActivity(new Intent(this, ClientServicesActivity.class));
                }
                else {
                    startActivity(new Intent(PivotActivity.this,CompleteperfilActivity.class));

                }
                finish();
            }

            if (type.equals("2")){
                startActivity(new Intent(this,AdminServiciesActivity.class));
                finish();
            }
        }

        else {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }
}
