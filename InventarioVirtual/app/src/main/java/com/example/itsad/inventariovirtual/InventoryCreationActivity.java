package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.itsad.inventariovirtual.Models.Inventario;
import com.example.itsad.inventariovirtual.Repositorio.InventarioRepository;

public class InventoryCreationActivity extends Activity {

    EditText editTextDescricao;
    EditText editTextNome;
    InventarioRepository inventarioRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_creation);

        editTextDescricao = (EditText)findViewById(R.id.descricaoInventario);

        editTextNome = (EditText)findViewById(R.id.nomeInventario);

        inventarioRepository = new InventarioRepository(this);

        ImageButton buttonAdd = (ImageButton)findViewById(R.id.saveInventory);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveItens();
            }
        });

    }

    public void saveItens() {
        try{
            Inventario i = new Inventario();
            i.setDescricao(editTextDescricao.getText().toString());
            i.setNome(editTextNome.getText().toString());
            int what = inventarioRepository.insert(i);
            Intent intent = new Intent(this, ItemActivity.class);
            intent.putExtra("_id", String.valueOf(what));
            startActivity(intent);
        } catch(Exception erro){
            Toast.makeText(this, erro.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            editTextDescricao.setText("");
            editTextNome.setText("");
        }


    }
}
