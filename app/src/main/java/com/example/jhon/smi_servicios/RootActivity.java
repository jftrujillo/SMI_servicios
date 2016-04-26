package com.example.jhon.smi_servicios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jhon.smi_servicios.Models.users;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;

public class RootActivity extends AppCompatActivity {
    public MobileServiceClient mClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        try {
            mClient = new MobileServiceClient(
                    "https://smiserviciosmovil.azure-mobile.net/",
                    "qIufyUhXNGYkLUXenUUDufQFPMdcUm65",
                    this
            );
            createNewUser();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("Azure", "Error conectado el cliente");
        }
        createNewUser();
    }

    public void createNewUser() {
        users user = new users();
        user.setName("jhon");
        user.setMail("admin123");
        user.setPassword("admin12345");
        user.setType(1);
        user.setId(1);
        mClient.getTable(users.class).insert(user, new TableOperationCallback<users>() {
            @Override
            public void onCompleted(users entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                    Log.i("AZURE", "usuario creado");
                } else {
                    // Insert failed
                    Log.i("AZURE", "usuario no creado");
                }
            }
        });


    }
}

