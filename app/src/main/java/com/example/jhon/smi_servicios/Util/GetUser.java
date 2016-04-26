package com.example.jhon.smi_servicios.Util;

import com.example.jhon.smi_servicios.Models.Users;

/**
 * Created by jhon on 25/04/16.
 */
public class GetUser {
    Users user;
    String name;
    String password;

    public GetUser(String name, String password) {
        this.user = new Users();
        this.name = name;
        this.password = password;
    }

    public Users LoginUser(){
        //Obtiene el usuario por medio de nombre y contraseña
        return null;
    }
}
