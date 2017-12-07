package com.example.helio.gotochurchmobileproject.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dayvson on 02/12/17.
 */

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "gtc.db";
    private static final String NOME_BANCO2 = "gtcChurch.db";
    public int tb = 0;
    //usuario
    public static final String TABELA = "user";

    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    public static final String JSON = "json";
    public static final String ID_WS = "id_ws";//Id do webservice

    //congregacao
    public static final String TABELA_2 ="church";
    public static final String CNOME = "nome";
    public static final String COORDENADOR = "coordenador";
    public static final String ASSENTOS = "assentos";
    public static final String RUA = "rua";
    public static final String BAIRRO = "bairro";
    public static final String CIDADE = "cidade";
    public static final String COMP = "complemento";
    public static final String NUM = "numero";
    public static final String LAT = "latitude";
    public static final String LGT = "longitude";
    public static final String DADOS = "dados";


    public CriaBanco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CriaBanco(Context context, int tb, String db){
        /*String db = "";
        if(tb == 1) {

        } */
        super(context, db, null, 1);

        this.tb = tb;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        if(tb == 1) {
            sql = "CREATE TABLE " + TABELA + " ( "
                    + ID + " integer primary key autoincrement, "
                    + NOME + " text, "
                    + EMAIL + " text, "
                    + SENHA + " text, "
                    + JSON + " text, "
                    + ID_WS + " integer "
                    + ");";
            db.execSQL(sql);
        }else if(tb == 2){
            sql = "CREATE TABLE " + TABELA_2 + " ( "
                    + ID + " integer primary key autoincrement, "
                    + DADOS + " text, "
                    + CNOME + " text, "
                    + COORDENADOR + " text, "
                    + ASSENTOS + " text, "
                    + RUA + " text, "
                    + BAIRRO + " text, "
                    + CIDADE + " text, "
                    + COMP + " text, "
                    + NUM + " text, "
                    + LAT + " text, "
                    + LGT + " text "
                    + ");";
            db.execSQL(sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        if(tb == 1){
            sql = "DROP TABLE IF EXISTS" + TABELA;
        } else if(tb == 2){
            sql = "DROP TABLE IF EXISTS" + TABELA_2;
        }
        db.execSQL(sql);
        onCreate(db);
    }
}
