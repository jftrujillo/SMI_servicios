package com.example.jhon.smi_servicios.Util;

import android.os.AsyncTask;

import com.example.jhon.smi_servicios.Models.Users;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.ExecutionException;

/**
 * Created by jhon on 25/04/16.
 */
public class GetUser {
    Users user;
    String name;
    String password;
    MobileServiceClient client;
    MobileServiceTable<Users> mUsersList;
    MobileServiceList<Users> userses;
    onUserFindI callback;


    public interface onUserFindI{
         Users onUserFind(MobileServiceList users);
    }



    public GetUser(String name, String password, MobileServiceClient mobileServiceClient, onUserFindI callback) {
        this.user = new Users();
        this.name = name;
        this.password = password;
        this.client = mobileServiceClient;
        mUsersList = mobileServiceClient.getTable(Users.class);
        this.callback = callback;
    }

    public void LoginUser(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.onUserFind(userses);


            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                     userses = mUsersList.where().execute().get();

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
