package com.example.schoolaccesscode.entity;

public class RegistroAluno {
    private String nome;
    private String data;
    private Integer estaNaEscola;

    public RegistroAluno() {
    }

    public RegistroAluno(String nome, String data, Integer entrou) {
        this.nome = nome;
        this.data = data;
        this.estaNaEscola = entrou;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getEntrou() {
        return estaNaEscola;
    }

    public void setEstaNaEscola(Integer estaNaEscola) {
        this.estaNaEscola = estaNaEscola;
    }

    @Override
    public String toString() {
        String status = (estaNaEscola == 1) ? "saiu" : "entrou";
        return nome + " " + status + " às " + data;
    }
}
