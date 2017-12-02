package com.example.helio.gotochurchmobileproject.Basic;

import java.io.Serializable;

/**
 * Created by dayvson on 25/11/17.
 */

public class Setor implements Serializable{
    private int id;
    private int numero;
    private Coordenador coordenador;
    private Area area;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Area getArea() { return area; }

    public void setArea(Area area) { this.area = area; }
}
