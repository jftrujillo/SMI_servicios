package com.example.jhon.smi_logistica.Net;

import android.os.AsyncTask;

import com.example.jhon.smi_logistica.Models.Services;
import com.example.jhon.smi_logistica.Util.Constants;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.ExecutionException;

/**
 * Created by jhon on 20/05/16.
 */
public class ServicesDao {
    public static int QUERY_COMPLETED = 0;
    public static int QUERY_FAILED = 1;


    MobileServiceClient mClient;
    MobileServiceTable<Services> mTable;
    MobileServiceList<Services> mList;
    OnDataBaseResponse dataBaseResponse;


    public interface OnDataBaseResponse{
       void onCompletedQuery(int stateResult,MobileServiceList<Services> data);

    }


    public ServicesDao(MobileServiceClient client) {
        this.mClient = client;
        mTable = client.getTable(Services.class);

    }

    public void getAllServices(final OnDataBaseResponse dataBaseResponse){
        this.dataBaseResponse = dataBaseResponse;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mList = mTable.where().field("_deleted").eq(false).execute().get();
                } catch (InterruptedException e) {
                    dataBaseResponse.onCompletedQuery(QUERY_FAILED,null);
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    dataBaseResponse.onCompletedQuery(QUERY_FAILED,null);

                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dataBaseResponse.onCompletedQuery(QUERY_COMPLETED,mList);
            }
        }.execute();

    }

    public void getHomeAsistenceServices(final OnDataBaseResponse dataBaseResponse){
        this.dataBaseResponse = dataBaseResponse;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mList = mTable.where().field("type").eq(Constants.HOME_SERVICES).execute().get();
                } catch (InterruptedException e) {
                    dataBaseResponse.onCompletedQuery(QUERY_FAILED,null);
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    dataBaseResponse.onCompletedQuery(QUERY_FAILED,null);
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dataBaseResponse.onCompletedQuery(QUERY_COMPLETED,mList);
            }
        }.execute();

    }

    public void getRoadAssitenceServices(final OnDataBaseResponse dataBaseResponse){
        this.dataBaseResponse = dataBaseResponse;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mList = mTable.where().field("type").eq(Constants.ROAD_ASISTENCE_SERVICES).execute().get();
                } catch (InterruptedException e) {
                   dataBaseResponse.onCompletedQuery(QUERY_FAILED,null);
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    dataBaseResponse.onCompletedQuery(QUERY_FAILED,null);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dataBaseResponse.onCompletedQuery(QUERY_COMPLETED,mList);
            }
        }.execute();

    }


}
