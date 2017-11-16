package com.example.itsad.inventariovirtual.Models;

/**
 * Created by itsad on 14/11/2017.
 */

public class Item {

    // atributos
    private String _descricao;
    private String _imagem;
    private Inventario _inventario;
    private int _id;

    // Propriedades
    public void setDescricao(String descricao){
        _descricao = descricao;
    }
    public String getDescricao(){
        return this._descricao;
    }
    public void setImagem(String imagem){
        _imagem = imagem;
    }
    public String getImagem(){
        return this._imagem;
    }
    public void setId(int id){
        _id = id;
    }
    public int getId(){
        return this._id;
    }
    public void setInventario(Inventario i){ _inventario = i;}
    public Inventario getInventario(){return this._inventario;}
}
