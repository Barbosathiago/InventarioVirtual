package com.example.itsad.inventariovirtual.Models;

/**
 * Created by itsad on 14/11/2017.
 */

public class Pessoa {

    // Atributos

    private Inventario _inventario;
    private int _situacao;

    // Propriedades
    public void setInventario(Inventario inventario){
        this._inventario = inventario;
    }
    public Inventario getInventario(){
        return this._inventario;
    }
    public void setSituacao(int situacao){
        this._situacao = situacao;
    }
    public int getSituacao(){
        return this._situacao;
    }

    // Helpers
    public int PARADO(){
        return 0;
    }
    public int EMMOVIMENTO(){
        return 1;
    }
}
