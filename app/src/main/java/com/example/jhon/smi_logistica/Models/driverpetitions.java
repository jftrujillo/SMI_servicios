package com.example.jhon.smi_logistica.Models;

/**
 * Created by jhon on 21/05/16.
 */
public class driverpetitions {
    public static final int PETITION_PENDING = 0;
    public static final int PETITION_TAKEN = 1;
    public static final int PETITION_REFUSED = 2 ;

    String actualaddress;
    String futureaddress;
    String id;
    String insuranceid;
    int state;
    String time;
    String userid;
    String code;
    String date;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    public String getActualaddress() {
        return actualaddress;
    }

    public void setActualaddress(String actualaddress) {
        this.actualaddress = actualaddress;
    }

    public String getFutureaddress() {
        return futureaddress;
    }

    public void setFutureaddress(String futureaddress) {
        this.futureaddress = futureaddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(String insuranceid) {
        this.insuranceid = insuranceid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    //endregion
}
