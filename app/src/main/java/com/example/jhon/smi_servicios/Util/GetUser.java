package com.example.jhon.smi_servicios.Util;

import com.example.jhon.smi_servicios.Models.users;

/**
 * Created by jhon on 25/04/16.
 */
public class GetUser {
    users user;
    String name;
    String password;

    public GetUser(String name, String password) {
        this.user = new users();
        this.name = name;
        this.password = password;
    }

    public users LoginUser(){
        //Obtiene el usuario por medio de nombre y contraseña
        return null;
    }
}
