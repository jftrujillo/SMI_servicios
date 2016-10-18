package com.example.jhon.smi_servicios.Util;

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
}
