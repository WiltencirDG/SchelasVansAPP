package com.schelas.schelasvans.model;

import java.io.Serializable;

public class Passageiros implements Serializable {

    private String idPass;
    private String name;
    private int idImage;

    public Passageiros(String name, String idPass, Integer idImage){
        setIdImage(idImage);
        setName(name);
        setIdPass(idPass);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getIdPass() {
        return idPass;
    }

    public void setIdPass(String idPass) {
        this.idPass = idPass;
    }
}