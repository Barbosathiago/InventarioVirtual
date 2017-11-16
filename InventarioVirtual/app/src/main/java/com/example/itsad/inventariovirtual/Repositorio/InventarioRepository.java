package com.example.itsad.inventariovirtual.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.itsad.inventariovirtual.Banco.Conexao;
import com.example.itsad.inventariovirtual.Models.Inventario;

/**
 * Created by itsad on 14/11/2017.
 */

public class InventarioRepository {
    private static final String NOME_TABELA = "tb_inventario";
    private static final String[] COLUNAS = new String[]{"descricao","nome", "_id"};
    public static final String SCRIPT_TB_INVENTARIO ="  CREATE TABLE tb_inventario (\n" +
            "descricao varchar,\n" +
            "_id integer PRIMARY KEY AUTOINCREMENT,\n" +
            "nome text\n" +
            ");\n";

    Conexao conexaoOpenHelper;
    SQLiteDatabase db;
    Cursor cursor;
    Context _context;

    public InventarioRepository(Context context){
        conexaoOpenHelper = new Conexao(context);
        db = conexaoOpenHelper.getWritableDatabase();
        _context = context;
    }

    public int insert(Inventario i){
        try{
            ContentValues values = getContentValues(i);
            int id = (int) db.insert(NOME_TABELA, null, values);
            return id;
        } catch(Exception erro){
            ShowMessage(erro.getMessage());
            return -1;
        } finally {
            conexaoOpenHelper.close();
            db.close();
        }
    }

    public Inventario getInventarioById(long id){
        try{
            Inventario i = new Inventario();
            Cursor c = db.rawQuery("SELECT * FROM " + NOME_TABELA + " WHERE _id = ?", new String[]{String.valueOf(id)});
            if(c.moveToFirst()) {
                i.setDescricao(c.getString(c.getColumnIndex("descricao")));
                i.setNome(c.getString(c.getColumnIndex("nome")));
                i.setId(c.getInt(c.getColumnIndex("_id")));
            }
            c.close();
            return i;
        } catch (Exception e){
            Toast.makeText(_context, e.getMessage(), Toast.LENGTH_LONG);
            return null;
        }
    }
    public Cursor getAll(){
        return db.query(NOME_TABELA, COLUNAS, null, null, null, null, null);
    }

    private ContentValues getContentValues(Inventario i){
        ContentValues values = new ContentValues();
        values.put("descricao", i.getDescricao());
        values.put("nome", i.getNome());
        return values;
    }
    private void ShowMessage(String mensagem){
        Toast.makeText(_context,mensagem,Toast.LENGTH_LONG).show();
    }
    public int deleteRecord(long id){
        return db.delete(NOME_TABELA, "_id = ?", new String[]{String.valueOf(id)});
    }



}
