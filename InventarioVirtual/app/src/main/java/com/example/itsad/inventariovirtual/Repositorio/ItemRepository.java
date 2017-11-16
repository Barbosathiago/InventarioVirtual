package com.example.itsad.inventariovirtual.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.itsad.inventariovirtual.Banco.Conexao;
import com.example.itsad.inventariovirtual.Models.Item;

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
            "fk_inventario integer\n" +
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

    public Item getItemById(long id){
        try {
            Item i = null;
            Cursor c = db.rawQuery("SELECT * FROM " + NOME_TABELA + " WHERE _id = ?", new String[]{String.valueOf(id)});
            i.setDescricao(c.getString(cursor.getColumnIndex("descricao")));
            i.setImagem(c.getString(cursor.getColumnIndex("imagem")));
            i.setId(c.getInt(cursor.getColumnIndex("_id")));
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
        return values;
    }

    private void ShowMessage(String mensagem){
        Toast.makeText(_context, mensagem, Toast.LENGTH_LONG).show();
    }
}
