package com.example.schoolaccesscode.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Aluno implements Parcelable {
    private String matricula;
    private String nome;
    private String ano;
    private String turno;
    private String nomeDoPai;
    private String nomeDaMae;
    private boolean estaNaEscola;

    public Aluno() {
    }

    public Aluno(String matricula, String nome, String ano, String turno, String nomeDoPai, String nomeDaMae, boolean estaNaEscola) {
        this.matricula = matricula;
        this.nome = nome;
        this.ano = ano;
        this.turno = turno;
        this.nomeDoPai = nomeDoPai;
        this.nomeDaMae = nomeDaMae;
        this.estaNaEscola = estaNaEscola;
    }

    protected Aluno(Parcel in) {
        matricula = in.readString();
        nome = in.readString();
        ano = in.readString();
        turno = in.readString();
        nomeDoPai = in.readString();
        nomeDaMae = in.readString();
    }

    public static final Creator<Aluno> CREATOR = new Creator<Aluno>() {
        @Override
        public Aluno createFromParcel(Parcel in) {
            return new Aluno(in);
        }

        @Override
        public Aluno[] newArray(int size) {
            return new Aluno[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public boolean isEstaNaEscola() {
        return estaNaEscola;
    }

    public void setEstaNaEscola(boolean estaNaEscola) {
        this.estaNaEscola = estaNaEscola;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(matricula);
        dest.writeString(nome);
        dest.writeString(ano);
        dest.writeString(turno);
        dest.writeString(nomeDoPai);
        dest.writeString(nomeDaMae);
    }
}
