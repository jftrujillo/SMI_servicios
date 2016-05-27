package com.example.jhon.smi_servicios.Models;

import java.sql.Date;

/**
 * Created by jhon on 21/05/16.
 */
public class driverpetitions {
    public final int PETITION_PENDING = 0;
    public final int PETITION_TAKEN = 1;
    public final int PETITION_REFUSED = 2 ;

    String actualaddress;
    Date date;
    String futureaddress;
    String id;
    String insuranceid;
    String servicename;
    int state;
    String time;
    String userid;
    //region Getters and Setters
    public String getActualaddress() {
        return actualaddress;
    }

    public void setActualaddress(String actualaddress) {
        this.actualaddress = actualaddress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
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
