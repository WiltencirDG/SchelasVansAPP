package com.schelas.schelasvans.model;

public class Passageiro {

    private String nome;
    private int idade;
    private char sexo;

    public Passageiro(String nome, int idade, char sexo) {
        setNome(nome);
        setIdade(idade);
        setSexo(sexo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
}
