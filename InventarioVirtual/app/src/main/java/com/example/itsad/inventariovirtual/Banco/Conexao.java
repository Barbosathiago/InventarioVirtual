package com.example.itsad.inventariovirtual.Banco;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.itsad.inventariovirtual.Repositorio.InventarioRepository;
import com.example.itsad.inventariovirtual.Repositorio.ItemRepository;

/**
 * Created by itsad on 14/11/2017.
 */

public class Conexao extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_INVENTARIOVIRTUAL2017";
    private static final int DB_VERSION = 1;
    private Context _context;

    public Conexao(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(InventarioRepository.SCRIPT_TB_INVENTARIO);
            db.execSQL(ItemRepository.SCRIPT_TB_ITEM);
            Toast.makeText(_context, "BANCO CRIADO COM SUCESSO!", Toast.LENGTH_LONG).show();
        } catch (SQLException erro){
            Toast.makeText(_context, "ERRO " + erro.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // implementacao
    }
}
