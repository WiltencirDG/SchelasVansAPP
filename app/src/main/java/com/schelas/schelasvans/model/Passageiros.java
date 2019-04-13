package com.schelas.schelasvans.model;

import java.io.Serializable;

public class Passageiros implements Serializable {

    private String idPass;
    private String name;
    private String Address;
    private String AddressNumber;
    private String Phone;

    public Passageiros(String name, String idPass, String Address, String AddressNumber, String Phone ){
        setName(name);
        setIdPass(idPass);
        setPhone(Phone);
        setAddress(Address);
        setAddressNumber(AddressNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdPass() {
        return idPass;
    }

    public void setIdPass(String idPass) {
        this.idPass = idPass;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setAddressNumber(String addressNumber) {
        AddressNumber = addressNumber;
    }

    public String getAddressNumber() {
        return AddressNumber;
    }
}