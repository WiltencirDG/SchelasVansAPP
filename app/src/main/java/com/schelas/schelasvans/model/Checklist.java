package com.schelas.schelasvans.model;

import java.io.Serializable;

public class Checklist implements Serializable {

    private Integer VeiculoId;
    private Integer PassageiroId;
    private Boolean isSelected;

    public Checklist(Integer veiculoId, Integer passageiroId, Boolean isSelected) {
        VeiculoId = veiculoId;
        PassageiroId = passageiroId;
        this.isSelected = isSelected;
    }

    public Integer getVeiculoId() {
        return VeiculoId;
    }

    public void setVeiculoId(Integer veiculoId) {
        VeiculoId = veiculoId;
    }

    public Integer getPassageiroId() {
        return PassageiroId;
    }

    public void setPassageiroId(Integer passageiroId) {
        PassageiroId = passageiroId;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
