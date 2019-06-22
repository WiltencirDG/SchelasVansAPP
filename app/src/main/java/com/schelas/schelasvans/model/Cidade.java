package com.schelas.schelasvans.model;

public class Cidade {
    private Integer Id;
    private String nome;

    public Cidade(Integer id, String nome) {
        setId(id);
        setNome(nome);
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
