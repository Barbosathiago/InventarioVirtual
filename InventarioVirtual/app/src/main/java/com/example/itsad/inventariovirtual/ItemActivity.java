package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.itsad.inventariovirtual.Models.Inventario;
import com.example.itsad.inventariovirtual.Repositorio.InventarioRepository;
import com.example.itsad.inventariovirtual.Repositorio.ItemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemActivity extends Activity {
    ListView mListViewItens;
    ItemRepository itemRepository;
    Inventario mInventario;
    InventarioRepository inventarioRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        itemRepository = new ItemRepository(this);
        inventarioRepository = new InventarioRepository(this);
        mListViewItens = (ListView)findViewById(R.id.inventariosExistentes);
        Bundle extras = getIntent().getExtras();
        String id = getIntent().getStringExtra("_id");
        mInventario = inventarioRepository.getInventarioById(Long.parseLong(id));
        mListViewItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateItem(l);
            }
        });

        updateList();
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateList();
    }

    public void updateItem(long id){
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("_id", String.valueOf(mInventario.getId()));
        intent.putExtra("_idItem", String.valueOf(id));
        intent.putExtra(AddItemActivity.ISUPDATE, "UPDATE");
        startActivity(intent);
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("_id", String.valueOf(mInventario.getId()));
        startActivity(intent);
    }
    private void updateList(){
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, itemRepository.getItensByInventario(mInventario.getId()), new String[]{"descricao"}, new int[]{android.R.id.text1},0);
        mListViewItens.setAdapter(simpleCursorAdapter);
    }

    public void StartMonitoring(View view) {
        Intent intent = new Intent(this, MonitoringActivity.class);
        intent.putExtra("_idInventario", String.valueOf(mInventario.getId()));
        startActivity(intent);
    }

    public void finalizarMonitoramento(View view) {
        Intent intent = new Intent(this, CheckItensActivity.class);
        intent.putExtra("_idInventario", String.valueOf(mInventario.getId()));
        startActivity(intent);
    }
}
