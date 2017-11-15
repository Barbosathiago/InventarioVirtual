package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.itsad.inventariovirtual.Repositorio.InventarioRepository;

import java.util.List;

public class SelectInventoryActivity extends Activity {

    ListView mListViewInventarios;
    InventarioRepository inventarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_inventory);

        inventarioRepository = new InventarioRepository(this);
        mListViewInventarios = (ListView)findViewById(R.id.listInventarios);
        updateList();
    }

    private void updateList(){
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, inventarioRepository.getAll(), new String[]{"nome"}, new int[]{android.R.id.text1},0);
        mListViewInventarios.setAdapter(simpleCursorAdapter);
    }

}
