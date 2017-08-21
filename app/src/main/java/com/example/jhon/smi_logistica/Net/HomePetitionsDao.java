package com.example.jhon.smi_logistica.Net;

import android.os.AsyncTask;

import com.example.jhon.smi_logistica.Models.Homepetitions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.ExecutionException;

/**
 * Created by jhon on 21/05/16.
 */
public class HomePetitionsDao {
    public static int QUERY_COMPLETED = 0;
    public static int QUERY_FAILED = 1;

    OnDataBaseResponse dataBaseResponse;
    MobileServiceClient mClient;
    MobileServiceTable<Homepetitions> mTable;
    MobileServiceList<Homepetitions> mList;


    public HomePetitionsDao(MobileServiceClient mClient) {
        this.mClient = mClient;
        mTable = mClient.getTable(Homepetitions.class);
    }

    public void getAllHomePetitions(final OnDataBaseResponse dataBaseResponse){
        this.dataBaseResponse = dataBaseResponse;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mList = mTable.where().field("_deleted").eq(false).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    dataBaseResponse.onQueryCompleted(QUERY_FAILED,null);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    dataBaseResponse.onQueryCompleted(QUERY_FAILED,null);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dataBaseResponse.onQueryCompleted(QUERY_COMPLETED,mList);
            }
        }.execute();



    }

    public void createNewPetition(final Homepetitions homepetitions, final OnDataBaseResponse dataBaseResponse){
        this.dataBaseResponse = dataBaseResponse;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mTable.insert(homepetitions).get();
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
                if (homepetitions.getId() != null){
                    dataBaseResponse.onQueryCompleted(QUERY_COMPLETED,null);
                }
                else {
                    dataBaseResponse.onQueryCompleted(QUERY_FAILED,null);
                }


            }
        }.execute();

    }

    public interface OnDataBaseResponse{
     void onQueryCompleted(int stateResult, MobileServiceList<Homepetitions> data);
    }
}
