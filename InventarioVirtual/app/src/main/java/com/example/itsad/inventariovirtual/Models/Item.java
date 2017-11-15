package com.example.itsad.inventariovirtual.Models;

/**
 * Created by itsad on 14/11/2017.
 */

public class Item {

    // atributos
    private String _descricao;
    private int _imagem;
    private int _id;

    // Propriedades
    public void setDescricao(String descricao){
        _descricao = descricao;
    }
    public String getDescricao(){
        return this._descricao;
    }
    public void setImagem(int imagem){
        imagem = _imagem;
    }
    public int getImagem(){
        return this._imagem;
    }
    public void setId(int id){
        _id = id;
    }
    public int getId(){
        return this._id;
    }
}
