package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
        mListViewInventarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SelectInventoryActivity.this, "Records deleted = " + inventarioRepository.deleteRecord(l),Toast.LENGTH_SHORT).show();
                updateList();
                return true;
            }
        });
        updateList();
    }

    private void updateList(){
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, inventarioRepository.getAll(), new String[]{"nome"}, new int[]{android.R.id.text1},0);
        mListViewInventarios.setAdapter(simpleCursorAdapter);
    }
}
