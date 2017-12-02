package com.example.helio.gotochurchmobileproject.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.helio.gotochurchmobileproject.Basic.User;

/**
 * Created by dayvson on 02/12/17.
 */

public class DAOUser {

    private SQLiteDatabase db;
    private CriaBanco banco;


    public DAOUser(Context context){
        banco = new CriaBanco(context);
    }


    public String insereDado(String nome, String email, String senha, int id_ws){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.NOME, nome);
        valores.put(CriaBanco.EMAIL, email);
        valores.put(CriaBanco.SENHA, senha);
        valores.put(CriaBanco.ID_WS, id_ws);

        resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();

        if(resultado == -1){
            return "Erro ao inserir registro";
        }else {
            return "Usuario registrado com sucesso";
        }
    }

    public String insereUsuario(User u){
        return this.insereDado(u.getName(),u.getEmail(), u.getPassword(), u.getId());
    }

    public Cursor carregaDados(){

        Cursor cursor;
        String[] campos = {banco.ID, banco.NOME, banco.SENHA};

        db = banco.getReadableDatabase();

        cursor = db.query(banco.TABELA, campos, null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }
}
