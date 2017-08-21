package com.example.jhon.smi_logistica.Net;

import android.os.AsyncTask;

import com.example.jhon.smi_logistica.Models.CarBorrow;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.ExecutionException;

/**
 * Created by jhon on 12/28/16.
 */

public class CarBorrowDao {
    public static final int INSERT_CORRECT = 0;
    public static final int INSERT_FAILED = 1;

    MobileServiceClient mClient;
    MobileServiceTable<CarBorrow> mTable;
    MobileServiceList<CarBorrow> mList;
    PetitionsResultI petitionsResultI;

    public CarBorrowDao(MobileServiceClient mClient, PetitionsResultI petitionsResultI) {
        this.mClient = mClient;
        this.mTable = mClient.getTable(CarBorrow.class);
        this.petitionsResultI = petitionsResultI;
    }

    public void createNewCarBorrowPetition(final CarBorrow carBorrow){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mTable.insert(carBorrow).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (carBorrow.getId() != null){
                    petitionsResultI.OnInsertFinished(INSERT_CORRECT, null);
                }
                else {
                    petitionsResultI.OnInsertFinished(INSERT_FAILED, null);
                }
            }
        }.execute();

    }
}
