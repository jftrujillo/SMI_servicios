package com.example.jhon.smi_logistica;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by jhon on 2/21/17.
 */

public class aplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
