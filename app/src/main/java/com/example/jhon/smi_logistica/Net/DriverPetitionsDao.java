package com.example.jhon.smi_logistica.Net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jhon.smi_logistica.Models.driverpetitions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.ExecutionException;

/**
 * Created by jhon on 25/07/16.
 */
public class DriverPetitionsDao {

    public static final int INSERT_CORRECT = 0;
    public static final int INSERT_FAILED = 1;

    MobileServiceClient mClient;
    MobileServiceTable<driverpetitions> mTable;
    MobileServiceList<driverpetitions> mList;
    PetitionsResultI petitionsResultI;


    public DriverPetitionsDao(MobileServiceClient mClient, PetitionsResultI petitionsResultI) {
        this.mClient = mClient;
        mTable = mClient.getTable(driverpetitions.class);
        this.petitionsResultI = petitionsResultI;
    }

    public void createNewDriverPetition(final driverpetitions driverPet){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    mTable.insert(driverPet).get();
                } catch (InterruptedException e) {
                    petitionsResultI.OnInsertFinished(INSERT_FAILED,e.toString());
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    petitionsResultI.OnInsertFinished(INSERT_FAILED,e.toString());
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (driverPet.getId() != null){
                    petitionsResultI.OnInsertFinished(INSERT_CORRECT,null);
                }
                else {
                    Log.i("AZURE_INSERT","FAILED");
                    petitionsResultI.OnInsertFinished(INSERT_FAILED,null);
                }

            }
        }.execute();


    }
}
