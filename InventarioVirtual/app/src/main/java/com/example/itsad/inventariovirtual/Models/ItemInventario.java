package com.example.itsad.inventariovirtual.Models;

/**
 * Created by itsad on 14/11/2017.
 */

public class ItemInventario {

    // Atributos
    private Item _item;
    private boolean _presente;
    private int _id;


    // Propriedades
    public void setItem(Item item){
        this._item = item;
    }
    public Item getItem(){
        return this._item;
    }
    public void setPresente(boolean status){
        this._presente = status;
    }
    public boolean getPresente(){
        return this._presente;
    }
    public void setId(int id){
        this._id = id;
    }
    public int getId(){
        return this._id;
    }

}
