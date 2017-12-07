package com.example.helio.gotochurchmobileproject.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.helio.gotochurchmobileproject.Basic.Address;
import com.example.helio.gotochurchmobileproject.Basic.Church;

import java.util.ArrayList;

/**
 * Created by dayvson on 06/12/17.
 */

public class DAOFavoritChurch {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public DAOFavoritChurch(Context context){
        banco = new CriaBanco(context, 2, "gtcChurch.db");
    }




    public boolean insereDado(String nome, String coordenador, String assentos, String rua, String bairro, String cidade, String complemento, String numero, String lat, String lgt, String dados){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.CNOME, nome);
        valores.put(CriaBanco.COORDENADOR, coordenador);
        valores.put(CriaBanco.ASSENTOS, assentos);
        valores.put(CriaBanco.RUA, rua);
        valores.put(CriaBanco.BAIRRO, bairro);
        valores.put(CriaBanco.CIDADE, cidade);
        valores.put(CriaBanco.COMP, complemento);
        valores.put(CriaBanco.NUM, numero);
        valores.put(CriaBanco.LAT, lat);
        valores.put(CriaBanco.LGT, lgt);
        valores.put(CriaBanco.DADOS, dados);

        resultado = db.insert(CriaBanco.TABELA_2, null, valores);
        db.close();

        if(resultado == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertChurch(Church c){
        boolean retorno = false;
        try{

            if(verificaDuplicidade(c)){
                retorno = false;
            }else{
                retorno = this.insereDado(c.getName(), c.getResponsible(), c.getAdjunct(), c.getAddress().getStreetName(), c.getAddress().getDistrict(), c.getAddress().getCity(), "", c.getAddress().getHomeNumber(), ""+c.getLat(), ""+c.getLng(), c.getDados());
            }

        }catch (Exception e){

        }

        return retorno;

    }


    public ArrayList<Church> carregaDados() throws Exception {

        Cursor cursor;
        String[] campos = {banco.ID, banco.CNOME, banco.COORDENADOR, banco.ASSENTOS, banco.RUA, banco.BAIRRO, banco.CIDADE, banco.COMP, banco.NUM, banco.LAT, banco.LGT, banco.DADOS};

        db = banco.getReadableDatabase();

        cursor = banco.getReadableDatabase().query(banco.TABELA_2, campos, null, null, banco.ID, null, null);

        ArrayList<Church> lc = null;


        try {

            if (cursor != null) {
                //int tam = cursor.getCount();
                lc = new ArrayList<Church>();
                while (cursor.moveToNext()){

                    Church c = new Church();
                    c.setId(cursor.getInt(cursor.getColumnIndex(banco.ID)));
                    c.setName(cursor.getString(cursor.getColumnIndex(banco.CNOME)));
                    c.setResponsible(cursor.getString(cursor.getColumnIndex(banco.COORDENADOR)));
                    c.setAdjunct(cursor.getString(cursor.getColumnIndex(banco.ASSENTOS)));

                    Address a = new Address();
                    a.setStreetName(cursor.getString(cursor.getColumnIndex(banco.RUA)));
                    a.setDistrict(cursor.getString(cursor.getColumnIndex(banco.BAIRRO)));
                    a.setCity(cursor.getString(cursor.getColumnIndex(banco.CIDADE)));
                    a.setHomeNumber(cursor.getString(cursor.getColumnIndex(banco.NUM)));

                    c.setAddress(a);
                    if(cursor.getString(cursor.getColumnIndex(banco.LAT)).equals("")){
                        c.setLat(0);
                    }else{
                        c.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex(banco.LAT))));
                    }

                    if(cursor.getString(cursor.getColumnIndex(banco.LGT)).equals("")){
                        c.setLng(0);
                    }else{
                        c.setLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(banco.LGT))));
                    }

                    c.setDados(cursor.getString(cursor.getColumnIndex(banco.DADOS)));

                    lc.add(c);


                }
                //cursor.moveToFirst();
            }

            db.close();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return lc;

    }

    public boolean verificaDuplicidade(Church c) throws Exception {
        Cursor cursor;
        String[] campos = {banco.ID, banco.CNOME, banco.COORDENADOR, banco.ASSENTOS, banco.RUA, banco.BAIRRO, banco.CIDADE, banco.COMP, banco.NUM, banco.LAT, banco.LGT, banco.DADOS};

        db = banco.getReadableDatabase();

        cursor = banco.getReadableDatabase().query(banco.TABELA_2, campos, banco.ID+"="+c.getId(), null, banco.ID, null, null);

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

    public boolean delete(Church c){
        String where = banco.ID+"="+c.getId();
        String[] campos = {banco.ID, banco.CNOME, banco.COORDENADOR, banco.ASSENTOS, banco.RUA, banco.BAIRRO, banco.CIDADE, banco.COMP, banco.NUM, banco.LAT, banco.LGT, banco.DADOS};
        db = banco.getWritableDatabase();
        /*if(db.delete(banco.TABELA_2, where, campos) > 0){
            return true;
        }else{
            return false;
        }*/

        db.execSQL("DELETE FROM "+ banco.TABELA_2+ " WHERE "+where);
        db.close();
        boolean retorno = false;

        try{
            retorno = verificaDuplicidade(c);
        }catch (Exception e){

        }

        if(retorno)
            return false;
        else
            return true;

    }
}
