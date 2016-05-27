package com.example.jhon.smi_servicios.Models;

/**
 * Created by jhon on 25/04/16.
 */
public class TypeUser {
    public static final int  CLIENT = 1;
    public static final int  ADMIN = 2;

    int typeid;
    String name;


    //region getters and setters
    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //endregion



}
