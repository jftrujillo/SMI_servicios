package com.example.jhon.smi_servicios.Util;

import android.content.Context;

import com.example.jhon.smi_servicios.R;

/**
 * Created by jhonfredy on 17/10/2016.
 */

public class StringsValidation {

    public static boolean ValidateString(String...paramgs){
        for (String paramg : paramgs) {
                if (paramg.startsWith(" ")){
                 return false;
                }
            if (paramg.equals("")){
                return false;
            }

                if (paramg.equals("Genero")){
                    return false;
                }
        }
        return true;
    }

    public static boolean ValidateDates(Context context, String... params){
        for (String param : params) {
            if (param.equals("00/00/0000") || param.equals(context.getString(R.string.fecha_no_seleccionada))){
                return false;
            }
        }
        return true;
    }
}
