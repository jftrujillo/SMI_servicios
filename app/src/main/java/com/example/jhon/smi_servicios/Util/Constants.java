package com.example.jhon.smi_servicios.Util;

import android.content.Context;

import com.example.jhon.smi_servicios.R;

/**
 * Created by jhon on 25/04/16.
 */
public class Constants {
    public static final String preferencesName = "PREFERENCIAS_SMI";
    public static final String isLoged = "IS_LOGED";
    public static final String isComplete = "IS_COMPLETE";
    public static final String userComplete = "IS_COMPLETE_USER";
    public static final String userID = "USER_ID";
    public static final String userEmai = "USER_EMAIL";
    public static final String typeUser = "USER_TYPE";
    public static final String password = "PASS";
    public static final int CLIENT = 1;
    public static final int ADMIN = 2;
    public static final String url = "https://smiserviciosmovil.azure-mobile.net/";
    public static final String key = "qIufyUhXNGYkLUXenUUDufQFPMdcUm65";
    public static final String userName = "USER_NAME";
    public static final Integer HOME_SERVICES = 1;
    public static final Integer DRIVER_SERVICES = 3;
    public static final Integer ROAD_ASISTENCE_SERVICES = 2;
    public static final String TYPE_CLIENT_SERVICES = "type_client_services";
    public static final String AZURE_MOBILE_SERVICE_URL = "https://smiserviciosmovil.azure-mobile.net/";
    public static final String AZURE_MOBILE_SERVICE_KEY = "qIufyUhXNGYkLUXenUUDufQFPMdcUm65";
    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_IMG_URL = "img_url";
    public static final String CODIGO_PROM = "codigo_prom";


    public static String[] getStringArrayFromFragment(String id, Context context) {
        switch (id) {
            case "BEC3F8C6-3788-4E16-9D3E-1309E8734991":
                return context.getResources().getStringArray(R.array.Plomería);
            case "0E980C91-A9D7-46D0-A835-F2C3B9BACF6E":
                return context.getResources().getStringArray(R.array.Asistencia_Electrica);

            case "675ABF9F-2003-4228-B447-AFEBC41F01F5":
                return context.getResources().getStringArray(R.array.CERRAJERÍA_HOGAR);

            case "068DA69D-16F4-40E9-801F-3338F8384B85":
                return context.getResources().getStringArray(R.array.VIDRIOS);

            case "5F283C26-1577-4583-BFAC-D34BA01D3434":
                return context.getResources().getStringArray(R.array.VIGILANCIA);

            case "7D756D94-9369-4AE2-B0D8-C55F5FBE050A":
                return context.getResources().getStringArray(R.array.ARREGLO_DE_FACHADAS);

            case "48D00472-1BE9-4E37-B9EB-3F057F3F6D93":
                return context.getResources().getStringArray(R.array.ASISTENCIA_JURÍDICA);

            case "45B18B75-90DB-4C98-9935-3C73A284B535":
                return context.getResources().getStringArray(R.array.ASISTENCIA_MÉDICA);

            case "A474EA08-BA85-4F12-B305-3F564C0A73C9":
                return context.getResources().getStringArray(R.array.GRUA);
            case "B4E6D3C3-0824-4422-9ACF-B61C8A8CAE95":
                return context.getResources().getStringArray(R.array.MECANICO);

            case "2AF3C208-8EE8-447E-877B-D83CAAB1A3B3":
                return context.getResources().getStringArray(R.array.CERRAJERIA_CARRO);

            case "19D209CB-2C14-484A-A26D-4D5D47D965F2":
                return context.getResources().getStringArray(R.array.CAMBIO_DE_LLANTA);

            case "98349252-11DD-4F55-855D-C8388BD29E0F":
                return context.getResources().getStringArray(R.array.DESPLAZAMIENTO_VIAL);

            case "913E9ABD-952F-41CE-8812-636164FEA602":
                return context.getResources().getStringArray(R.array.PASO_DE_CORRIENTE);

            case "5FD30672-B651-4D50-94EB-61AFCF924CE7":
                return context.getResources().getStringArray(R.array.COMBUSTIBLE);

            case "marca":
                return context.getResources().getStringArray(R.array.marcas);

            case "tipo":
                return context.getResources().getStringArray(R.array.tipo);
            case "gasolina":
                return context.getResources().getStringArray(R.array.gasolina);
            default:
                return context.getResources().getStringArray(R.array.otro);
        }
    }


}
