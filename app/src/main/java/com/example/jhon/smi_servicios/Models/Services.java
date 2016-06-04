package com.example.jhon.smi_servicios.Models;

import com.example.jhon.smi_servicios.Util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhon on 12/05/16.
 */
public class Services {
    public static final Integer HOME_SERVICES    = 1;
    public static final Integer   DRIVER_SERVICES  = 3;
    public static final Integer   ROAD_ASISTENCE_SERVICES  = 2;

    String id;
    String name;
    String imgurl;
    int type;

    //region Getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //endregion


    public static List<Services> getServicesHome(){
        List<Services> data = new ArrayList<>();
        Services servicio1 =  new Services();
        servicio1.setName("Plomeria");
        servicio1.setType(Constants.HOME_SERVICES);
        servicio1.setImgurl("http://asistirsolucionessas.com.co/wp-content/uploads/2015/10/3.jpg");
        data.add(servicio1);

        Services servicio2 =  new Services();
        servicio2.setName("Asistencia Electrica");
        servicio2.setType(Constants.HOME_SERVICES);
        servicio2.setImgurl("http://elperiodicodelaenergia.com/wp-content/uploads/2014/10/cuadro-electrico.jpg");
        data.add(servicio2);

        return data;
    }

    public static List<Services> getServicesRoadAsistence(){
        List<Services> data = new ArrayList<>();
        Services servicio1 =  new Services();
        servicio1.setName("Grua");
        servicio1.setType(Constants.ROAD_ASISTENCE_SERVICES);
        servicio1.setImgurl("http://autoperiscal.com/wp-content/uploads/2015/02/grua24horas.jpg");
        data.add(servicio1);

        Services servicio2 =  new Services();
        servicio2.setName("Mecanico");
        servicio2.setType(Constants.ROAD_ASISTENCE_SERVICES);
        servicio2.setImgurl("http://uprosanluis.edu.ar/uproweb/wp-content/uploads/2014/12/mecanico-700x460.jpg");
        data.add(servicio2);

        return data;


    }
}
