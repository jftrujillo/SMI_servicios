package com.example.jhon.smi_servicios.Models;

/**
 * Created by jhon on 25/04/16.
 */
public class Users {
    String name;
    String identifycard;
    String telephone;
    String cellphone;
    String mail;
    String id;
    String adress;
    String genre;
    String age;
    int type;
    String password;
    int isvalid;
    String promocion;
    boolean perfilcompletado;
    long creado;

    //IS VALID 0= no completado perfil, 1  es valido aun, 2 es rechazado.

    public int isvalid() {
        return isvalid;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }


    //region getters and setters


    public long getCreado() {
        return creado;
    }

    public void setCreado(long creado) {
        this.creado = creado;
    }

    public int getIsvalid() {
        return isvalid;
    }

    public boolean isPerfilcompletado() {
        return perfilcompletado;
    }

    public void setPerfilcompletado(boolean perfilcompletado) {
        this.perfilcompletado = perfilcompletado;
    }

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentifycard() {
        return identifycard;
    }

    public void setIdentifycard(String identifycard) {
        this.identifycard = identifycard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    //endregion



    //endregion
}
