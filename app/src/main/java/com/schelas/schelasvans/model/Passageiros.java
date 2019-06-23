package com.schelas.schelasvans.model;

import java.io.Serializable;

public class Passageiros implements Serializable {

    private String idPass;
    private String name;
    private String Address;
    private String AddressNumber;
    private String Phone;
    private String email;
    private String bairro;
    private String cidade;
    private Boolean isSelected;

    public Passageiros(){

    }

    public Passageiros(String name, String idPass, String Address, String AddressNumber, String Phone, String email, String bairro, String cidade ){
        setName(name);
        setIdPass(idPass);
        setPhone(Phone);
        setAddress(Address);
        setAddressNumber(AddressNumber);
        setEmail(email);
        setBairro(bairro);
        setCidade(cidade);
    }

    public Passageiros(String idPass, String name, String isSelected ){
        setName(name);
        setIdPass(idPass);
        if(isSelected.equals("1")){
            setSelected(true);
        }else{
            setSelected(false);
        }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}