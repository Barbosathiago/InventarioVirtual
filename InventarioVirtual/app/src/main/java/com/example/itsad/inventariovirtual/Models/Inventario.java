package com.example.itsad.inventariovirtual.Models;

/**
 * Created by itsad on 14/11/2017.
 */

public class Inventario {

    private String _descricao;
    private String _nome;
    private int _id;

    public void setDescricao(String descricao){
        this._descricao = descricao;
    }
    public String getDescricao(){
        return this._descricao;
    }
    public void setId(int id){ this._id = id;}
    public int getId(){ return this._id; }
    public void setNome(String nome){
        this._nome = nome;
    }
    public String getNome(){
        return this._nome;
    }
}
