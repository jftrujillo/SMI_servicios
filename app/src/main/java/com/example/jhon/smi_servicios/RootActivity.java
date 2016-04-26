package com.example.jhon.smi_servicios;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jhon.smi_servicios.Models.Users;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class RootActivity extends AppCompatActivity {
    public MobileServiceClient mClient;
    public MobileServiceTable<Users> mUsersTable;
    Users user = new Users();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        user.setName("jhon");
        user.setMail("client");
        user.setPassword("admin123");
        user.setType(1);


        try {
            mClient = new MobileServiceClient("https://smiserviciosmovil.azure-mobile.net/",
                    "qIufyUhXNGYkLUXenUUDufQFPMdcUm65",
                    this);

            mUsersTable = mClient.getTable(Users.class);
            mClient.getTable(Users.class);
            Log.i("Azure", "table");
            newUser();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("Azure", "Error conectado cliente");
        }

    }
   public void newUser(){
       new AsyncTask<Void,Void,Void>(){


           @Override
           protected Void doInBackground(Void... params) {
               try {
                   mUsersTable.insert(user).get();
                   
                   Log.i("Azure",user.getId());

               } catch (InterruptedException e) {
                   e.printStackTrace();
               } catch (ExecutionException e) {
                   e.printStackTrace();
               }
               return null;
           }

       }.execute();

   }
}

