package com.example.jhon.smi_logistica.Models;

/**
 * Created by jhon on 21/05/16.
 */
public class Homepetitions {
    public static final int PETITION_PENDING = 0;
    public static final int PETITION_TAKEN = 1;
    public static final int PETITION_REFUSED = 2 ;


    String id;
    String servicename;
    String address;
    String carid;
    String description;
    String insuranceid;
    String randomcode;
    int state;
    String userid;
    String support_person;
    long creado;
    long fechaaceptada;

    public String getSupport_person() {
        return support_person;
    }

    public void setSupport_person(String support_person) {
        this.support_person = support_person;
    }

    //region Getters and Setters


    public long getFechaaceptada() {
        return fechaaceptada;
    }

    public void setFechaaceptada(long fechaaceptada) {
        this.fechaaceptada = fechaaceptada;
    }

    public long getCreado() {
        return creado;
    }

    public void setCreado(long creado) {
        this.creado = creado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(String insuranceid) {
        this.insuranceid = insuranceid;
    }

    public String getRandomcode() {
        return randomcode;
    }

    public void setRandomcode(String randomcode) {
        this.randomcode = randomcode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    //endregion
}
