package com.example.jhon.smi_servicios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jhon.smi_servicios.Models.Users;
import com.example.jhon.smi_servicios.Util.Constants;
import com.example.jhon.smi_servicios.Util.GetUser;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;

import java.net.MalformedURLException;

public class ClientServicesActivity extends AppCompatActivity {
MobileServiceClient mobileServiceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_services);
        try {
            mobileServiceClient = new MobileServiceClient(Constants.url,Constants.key,this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        GetUser getUser = new GetUser("jhon", "admin", mobileServiceClient, new GetUser.onUserFindI() {
            @Override
            public Users onUserFind(MobileServiceList users) {
                return null;
            }
        });

    }
}
