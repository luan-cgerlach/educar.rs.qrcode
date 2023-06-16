package com.example.schoolaccesscode.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AlunoLog {
    private String nome;
    private String data;
    private Integer estaNaEscola;

    DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public AlunoLog() {
    }

    public AlunoLog(String nome, String data, Integer entrou) {
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

    public String entrouOuSaiu(Integer estaNaEscola){
        if (estaNaEscola == 1){
            return "saiu";
        }
        return "entrou";
    }

    @Override
    public String toString() {
        String entrouOuSaiu = entrouOuSaiu(estaNaEscola);
        return ( nome+ " " +entrouOuSaiu+ " as " +data);
    }
}
