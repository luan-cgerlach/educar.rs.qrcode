package com.example.schoolaccesscode.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "escola.db";
    private static final int DATABASE_VERSION = 4;

    private static final String CREATE_TABLE_ALUNO =
            "CREATE TABLE aluno ("
                    + "matricula TEXT PRIMARY KEY,"
                    + "nome TEXT,"
                    + "ano TEXT,"
                    + "turno TEXT,"
                    + "nome_do_pai TEXT,"
                    + "nome_da_mae TEXT,"
                    + "estaNaEscola INTEGER DEFAULT 0"
                    + ")";

    private static final String CREATE_TABLE_LOG =
            "CREATE TABLE log ("
                    + "id INTEGER PRIMARY KEY,"
                    + "nome TEXT,"
                    + "data TEXT,"
                    + "estaNaEscola INTEGER"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUNO);
        db.execSQL(CREATE_TABLE_LOG);
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS aluno");
        db.execSQL("DROP TABLE IF EXISTS log");
        onCreate(db);
    }
}
