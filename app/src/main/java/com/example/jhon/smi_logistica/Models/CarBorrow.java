package com.example.jhon.smi_logistica.Models;

/**
 * Created by jhon on 12/28/16.
 */

public class CarBorrow {
    public static final int PETITION_PENDING = 0;
    public static final int PETITION_TAKEN = 1;
    public static final int PETITION_REFUSED = 2 ;

    String id;
    String datestart;
    String datefinish;
    String description;
    String numberspots;
    int state;
    String userid;
    String randomCode;
    long creado;
    long fechaaceptada;


    //region Getters and Setter


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

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public String getDatefinish() {
        return datefinish;
    }

    public void setDatefinish(String datefinish) {
        this.datefinish = datefinish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberspots() {
        return numberspots;
    }

    public void setNumberspots(String numberspots) {
        this.numberspots = numberspots;
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
