package com.example.jhon.smi_servicios.Models;

/**
 * Created by jhon on 21/05/16.
 */
public class Cars {
    String id;
    String userid;
    String brand;
    String line;
    String model;
    String carresgistration;

    //region Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarresgistration() {
        return carresgistration;
    }

    public void setCarresgistration(String carresgistration) {
        this.carresgistration = carresgistration;
    }

    //endregion
}
