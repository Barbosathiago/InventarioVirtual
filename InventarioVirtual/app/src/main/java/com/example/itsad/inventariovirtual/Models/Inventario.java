package com.example.itsad.inventariovirtual.Models;

/**
 * Created by itsad on 14/11/2017.
 */

public class Inventario {

    private ItemInventario _itemInventario;
    private String _descricao;
    private String _nome;
    private int _id;

    public void setDescricao(String descricao){
        this._descricao = descricao;
    }
    public String getDescricao(){
        return this._descricao;
    }
    public void setItemInventario(ItemInventario itemInventario){
        this._itemInventario = itemInventario;
    }
    public ItemInventario getItemInventario(){
        return this._itemInventario;
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
