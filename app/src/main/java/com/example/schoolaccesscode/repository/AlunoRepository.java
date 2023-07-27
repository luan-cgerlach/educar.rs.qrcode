package com.example.schoolaccesscode.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.schoolaccesscode.entity.Aluno;
import com.example.schoolaccesscode.entity.RegistroAluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {

    private SQLiteDatabase database;
    private SQLiteDatabase databaseLog;

    public AlunoRepository(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        databaseLog = dbHelper.getWritableDatabase();
    }

    public long inserirAluno(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("matricula", aluno.getMatricula());
        values.put("nome", aluno.getNome());
        values.put("ano", aluno.getAno());
        values.put("turno", aluno.getTurno());
        values.put("nome_do_pai", aluno.getNomeDoPai());
        values.put("nome_da_mae", aluno.getNomeDaMae());
        values.put("num_entradas", aluno.getNumeroDeEntradas());
        return database.insert("aluno", null, values);
    }

    public Aluno buscarAlunoPorId(String id) {
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query("aluno", null, "matricula = ?", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String matricula = cursor.getString(0);
            String nome = cursor.getString(1);
            String ano = cursor.getString(2);
            String turno = cursor.getString(3);
            String nomeDoPai = cursor.getString(4);
            String nomeDaMae = cursor.getString(5);
            Integer entrou = cursor.getInt(6);

            return new Aluno(matricula, nome, ano, turno, nomeDoPai, nomeDaMae, entrou);
        }
        return null;
    }

    public List<Aluno> buscarTodosAlunos() {
        List<Aluno> listaAlunos = new ArrayList<>();
        String orderBy = "nome ASC";
        Cursor cursor = database.query("aluno", null, null, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                String matricula = cursor.getString(0);
                String nome = cursor.getString(1);
                String ano = cursor.getString(2);
                String turno = cursor.getString(3);
                String nomeDoPai = cursor.getString(4);
                String nomeDaMae = cursor.getString(5);
                Integer entrou = cursor.getInt(6);

                Aluno aluno = new Aluno(matricula, nome, ano, turno, nomeDoPai, nomeDaMae, entrou);
                listaAlunos.add(aluno);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaAlunos;
    }

    public long atualizarAluno(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("ano", aluno.getAno());
        values.put("turno", aluno.getTurno());
        values.put("nome_do_pai", aluno.getNomeDoPai());
        values.put("nome_da_mae", aluno.getNomeDaMae());
        values.put("entrou", aluno.getNumeroDeEntradas());
        String[] selectionArgs = {aluno.getMatricula()};
        return database.update("aluno", values, "matricula = ?", selectionArgs);
    }

    public int deletarAluno(String id) {
        try {
            String[] selectionArgs = {String.valueOf(id)};
            return database.delete("aluno", "matricula = ?", selectionArgs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public void deletarTodosAlunos() {
        database.delete("aluno", null, null);
    }

    public long inserirRegistroDeEntradaDoAluno(RegistroAluno alunoLog) {
        ContentValues values = new ContentValues();
        values.put("nome", alunoLog.getNome());
        values.put("data", alunoLog.getData());
        values.put("num_entradas", alunoLog.getEntrou());
        return databaseLog.insert("log", null, values);
    }

    public List<RegistroAluno> buscarTodosRegistrosDeEntrada() {
        List<RegistroAluno> listaRegistroAluno = new ArrayList<>();
        Cursor cursor = databaseLog.query("log", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(1);
                String data = cursor.getString(2);
                Integer entrou = cursor.getInt(3);

                RegistroAluno alunolog = new RegistroAluno(nome, data, entrou);
                listaRegistroAluno.add(alunolog);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaRegistroAluno;
    }

    public void deletarTodosRegistros() {
        databaseLog.delete("log", null, null);
    }
}
