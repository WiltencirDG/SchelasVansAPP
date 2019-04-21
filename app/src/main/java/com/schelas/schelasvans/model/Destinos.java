package com.schelas.schelasvans.model;

import java.io.Serializable;

public class Destinos implements Serializable {
    private String rua;
    private String num;
    private String bairro;
    private String cidade;
    private String descricao;

    public Destinos(String descricao, String rua, String num, String bairro, String cidade) {
        setRua(rua);
        setNum(num);
        setBairro(bairro);
        setCidade(cidade);
        setDescricao(descricao);
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
