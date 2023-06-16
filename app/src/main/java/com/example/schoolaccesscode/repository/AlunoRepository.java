package com.example.schoolaccesscode.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.schoolaccesscode.entity.Aluno;
import com.example.schoolaccesscode.entity.AlunoLog;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlunoRepository {

    private SQLiteDatabase database;
    private SQLiteDatabase database_log;

    private AlunoLog alunoLog;
    DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    private LocalDateTime dateTime;

    public AlunoRepository(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        database_log = dbHelper.getWritableDatabase();
    }

    public long insert(Aluno aluno) {

        ContentValues values = new ContentValues();
        values.put("matricula", aluno.getMatricula());
        values.put("nome", aluno.getNome());
        values.put("ano", aluno.getAno());
        values.put("turno", aluno.getTurno());
        values.put("nome_do_pai", aluno.getNomeDoPai());
        values.put("nome_da_mae", aluno.getNomeDaMae());
        values.put("entrou", aluno.isEstaNaEscola() ? 1 : 0);
        return database.insert("aluno", null, values);
    }

    public Aluno queryById(String id) {
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query("aluno", null, "matricula = ?", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String matricula = cursor.getString(0);
            String nome = cursor.getString(1);
            String ano = cursor.getString(2);
            String turno = cursor.getString(3);
            String nomeDoPai = cursor.getString(4);
            String nomeDaMae = cursor.getString(5);
            String entrou = cursor.getString(6);

            return new Aluno(matricula, nome, ano, turno, nomeDoPai,nomeDaMae, Boolean.parseBoolean(entrou));
        }
        return null;
    }

    public List<Aluno> queryAll() {
        List<Aluno> listaAlunos = new ArrayList<>();

        Cursor cursor = database.query("aluno", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String matricula = cursor.getString(0);
                String nome = cursor.getString(1);
                String ano = cursor.getString(2);
                String turno = cursor.getString(3);
                String nomeDoPai = cursor.getString(4);
                String nomeDaMae = cursor.getString(5);
                String entrou = cursor.getString(6);

                Aluno aluno = new Aluno(matricula, nome, ano, turno, nomeDoPai, nomeDaMae, Boolean.parseBoolean(entrou));
                listaAlunos.add(aluno);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaAlunos;
    }

    public long update(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("ano", aluno.getAno());
        values.put("turno", aluno.getTurno());
        values.put("nome_do_pai", aluno.getNomeDoPai());
        values.put("nome_da_mae", aluno.getNomeDaMae());
        values.put("entrou", aluno.isEstaNaEscola());
        String[] selectionArgs = {aluno.getMatricula()};
        return database.update("aluno", values, "matricula = ?", selectionArgs);
    }

    public int delete(String id) {
        try {
            String[] selectionArgs = {String.valueOf(id)};
            return database.delete("aluno", "matricula = ?", selectionArgs);
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public void deleteAllAlunos() {
        database.delete("aluno", null, null);
    }

    public long insertLog(AlunoLog alunoLog) {
        ContentValues values = new ContentValues();
        values.put("nome", alunoLog.getNome());
        values.put("data", alunoLog.getData());
        values.put("entrou", alunoLog.getEntrou());
        return database_log.insert("log", null, values);
    }

    public List<AlunoLog> queryAllLog() {
        List<AlunoLog> listaAlunosLog = new ArrayList<>();

        Cursor cursor = database_log.query("log", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(1);
                String data = (cursor.getString(2));
                Integer entrou = cursor.getInt(3);

                AlunoLog alunolog = new AlunoLog(nome, data, entrou);
                listaAlunosLog.add(alunolog);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaAlunosLog;
    }

    public void deleteAllLog() {
        database_log.delete("log", null, null);
    }


}


