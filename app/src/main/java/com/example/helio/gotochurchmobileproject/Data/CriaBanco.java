package com.example.helio.gotochurchmobileproject.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dayvson on 02/12/17.
 */

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "gtc.db";
    public static final String TABELA = "user";

    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    public static final String ID_WS = "id_ws";//Id do webservice



    public CriaBanco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CriaBanco(Context context){
        super(context, NOME_BANCO, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE"+TABELA+"("
                +ID+" integer primary key autoincrement,"
                +NOME+" text,"
                +EMAIL+" text,"
                +SENHA+" text,"
                +ID_WS+" integer"
                +")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS" + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }
}
