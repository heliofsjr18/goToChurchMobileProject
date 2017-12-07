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
    private Context c;

    public DAOUser(Context context){
        banco = new CriaBanco(context, 1, "gtc.db");
        c = context;
    }


    public String insereDado(String nome, String email, String senha, String json, int id_ws){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.NOME, nome);
        valores.put(CriaBanco.EMAIL, email);
        valores.put(CriaBanco.SENHA, senha);
        valores.put(CriaBanco.JSON, json);
        valores.put(CriaBanco.ID_WS, id_ws);

        resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();

        if(resultado == -1){
            return "Erro ao inserir registro.";
        }else {
            return "Usuário registrado com sucesso.";
        }
    }

    public String insereUsuario(User u){
        return this.insereDado(u.getName(), u.getEmail(), u.getPassword(), u.getDados(), u.getId());
    }

    public User carregaDados() throws Exception {

        Cursor cursor;
        String[] campos = {banco.ID, banco.NOME, banco.SENHA, banco.EMAIL, banco.ID_WS, banco.JSON};

        db = banco.getReadableDatabase();

        cursor = banco.getReadableDatabase().query(banco.TABELA, campos, null, null, banco.ID, null, null);
        User u = null;

        try {

            if (cursor != null) {
                if (cursor.moveToNext()) {
                    u = new User();
                    u.setId(cursor.getInt(cursor.getColumnIndex(banco.ID)));
                    u.setName(cursor.getString(cursor.getColumnIndex(banco.NOME)));
                    u.setEmail(cursor.getString(cursor.getColumnIndex(banco.EMAIL)));
                    u.setPassword(cursor.getString(cursor.getColumnIndex(banco.SENHA)));
                    u.setDados(cursor.getString(cursor.getColumnIndex(banco.JSON)));
                }
                //cursor.moveToFirst();
            }

            db.close();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return u;
    }


    public boolean verifica(User u) throws Exception {
        Cursor cursor;
        String[] campos = {banco.ID, banco.NOME, banco.SENHA, banco.EMAIL, banco.ID_WS, banco.JSON};

        db = banco.getReadableDatabase();

        cursor = banco.getReadableDatabase().query(banco.TABELA, campos, banco.ID+"="+u.getId(), null, banco.ID, null, null);

        boolean retorno = false;

        try {

            if (cursor != null) {

                if (cursor.getCount() > 0){
                    retorno = true;
                }else{
                    retorno = false;
                }

            }

            db.close();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return retorno;

    }

    public boolean delete(User u){
        String where = banco.ID+"="+u.getId();
        String[] campos = {banco.ID, banco.NOME, banco.SENHA, banco.EMAIL, banco.ID_WS, banco.JSON};
        db = banco.getWritableDatabase();

        db.execSQL("DELETE FROM "+ banco.TABELA+ " WHERE "+where);
        db.close();
        boolean retorno = false;

        try{
            retorno = verifica(u);
        }catch (Exception e){

        }

        if(retorno)
            return false;
        else
            return true;

    }
}
