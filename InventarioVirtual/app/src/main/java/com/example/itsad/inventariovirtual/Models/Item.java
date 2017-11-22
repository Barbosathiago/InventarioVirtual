package com.example.itsad.inventariovirtual.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by itsad on 14/11/2017.
 */

public class Item {

    // atributos
    private String _descricao;
    private String _imagem;
    private Inventario _inventario;
    private int _presente;
    private int _id;
    public static final int FALSE = 0;
    public static final int TRUE = 1;

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
    public void setPresente(int presente){
        _presente = presente;
    }
    public int getPresente(){
        return _presente;
    }
    public Bitmap getPicture(){
        File imgFile = new File(_imagem);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        } else
            return null;
    }

}
