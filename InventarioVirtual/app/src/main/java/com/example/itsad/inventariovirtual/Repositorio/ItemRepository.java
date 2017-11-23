package com.example.itsad.inventariovirtual.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.itsad.inventariovirtual.Banco.Conexao;
import com.example.itsad.inventariovirtual.Models.Item;

import java.util.List;

/**
 * Created by itsad on 16/11/2017.
 */

public class ItemRepository {
    private static final String NOME_TABELA = "tb_item";
    private static final String[] COLUNAS = new String[]{"descricao", "_id", "imagem", "fk_inventario"};
    public static final String SCRIPT_TB_ITEM = "CREATE TABLE tb_item (\n" +
            "descricao text,\n" +
            "_id integer PRIMARY KEY AUTOINCREMENT,\n" +
            "imagem text,\n" +
            "fk_inventario integer,\n" +
            "presente integer\n" +
            ");";
    Conexao conexaoOpenHelper;
    SQLiteDatabase db;
    Cursor cursor;
    Context _context;

    public ItemRepository(Context context){
        conexaoOpenHelper = new Conexao(context);
        db = conexaoOpenHelper.getWritableDatabase();
        _context = context;
    }

    public int insert(Item i){
        int id = -1;
        try{
            ContentValues values = getContentValues(i);
            id = (int) db.insert(NOME_TABELA, null, values);
            return id;
        } catch (SQLException e){
            ShowMessage(e.getMessage());
            return id;

        } catch(Exception erro) {
            ShowMessage(erro.getMessage());
            return id;
        }
        finally {
            conexaoOpenHelper.close();
            db.close();
        }
    }

    public void updateItem(Item i){
        try{
            ContentValues values =getContentValues(i);
            db.update(NOME_TABELA,values, "_id = ?", new String[]{String.valueOf(i.getId())});
        }catch(SQLException e){
            ShowMessage(e.getMessage());
        } catch(Exception e){
            ShowMessage(e.getMessage());
        } finally {
            conexaoOpenHelper.close();
            db.close();
        }
    }

    public void setItensPresentStateByInventory(long idInventario, long state){
        try{
            ContentValues v = new ContentValues();
            v.put("presente", state);
            db.update(NOME_TABELA,v, "fk_inventario = ?", new String[]{String.valueOf(idInventario)});
        } catch (SQLException e){
            ShowMessage(e.getMessage());
        }
    }
    public void setItemPresentState(long id, long state){
        try{
            db.rawQuery("UPDATE "+NOME_TABELA+" SET presente = ? WHERE _id = ?",new String[]{String.valueOf(state), String.valueOf(id)} );
        }
        catch(SQLException e){
            ShowMessage(e.getMessage());
        }
    }

    public void toggleItemPresentState(long id){
        try{
            Cursor c = db.rawQuery("SELECT presente FROM " + NOME_TABELA + " WHERE _id = ?", new String[]{String.valueOf(id)});
            c.moveToFirst();
            ContentValues v = new ContentValues();
            if(c.getInt(c.getColumnIndex("presente")) == Item.FALSE) {

                v.put("presente", Item.TRUE);
                db.update(NOME_TABELA,v, "_id = ?", new String[]{String.valueOf(id)});
                //db.rawQuery("UPDATE " + NOME_TABELA + " SET presente = "+ String.valueOf(Item.TRUE) +" WHERE _id = " + String.valueOf(id), null);
            } else {
                //db.rawQuery("UPDATE " + NOME_TABELA + " SET presente = ? WHERE _id = ?", new String[]{ String.valueOf(Item.FALSE), String.valueOf(id)});
                v.put("presente", Item.FALSE);
                db.update(NOME_TABELA,v, "_id = ?", new String[]{String.valueOf(id)});
            }
        }
        catch(SQLException e){
            ShowMessage(e.getMessage());
        }
    }

    public Item getItemById(long id){
        try {
            Item i = new Item();
            Cursor c = db.rawQuery("SELECT * FROM " + NOME_TABELA + " WHERE _id = ?", new String[]{String.valueOf(id)});
            if(c.moveToFirst()){
                i.setDescricao(c.getString(c.getColumnIndex("descricao")));
                i.setImagem(c.getString(c.getColumnIndex("imagem")));
                i.setId(c.getInt(c.getColumnIndex("_id")));
                i.setPresente(c.getInt(c.getColumnIndex("presente")));
            }
            c.close();
            return i;
        } catch (Exception e){
            ShowMessage(e.getMessage());
            return null;
        }
    }

    public int deleteRecord(long id){
        try {
            return db.delete(NOME_TABELA, "_id = ?", new String[]{String.valueOf(id)});
        } catch (SQLException e)
        {
            ShowMessage(e.getMessage());
            return -1;
        }
    }

    public Cursor getAll(){ return db.query(NOME_TABELA, COLUNAS, null, null, null, null, null);}
    public Cursor getItensByInventario(long inventarioId){
        return db.rawQuery("SELECT * FROM "+NOME_TABELA + " WHERE fk_inventario = ?", new String[]{String.valueOf(inventarioId)});
    }

    private ContentValues getContentValues(Item i){
        ContentValues values = new ContentValues();
        values.put("descricao", i.getDescricao());
        values.put("imagem", i.getImagem());
        values.put("fk_inventario", i.getInventario().getId());
        values.put("presente", i.getPresente());
        return values;
    }

    private void ShowMessage(String mensagem){
        Toast.makeText(_context, mensagem, Toast.LENGTH_LONG).show();
    }
}
