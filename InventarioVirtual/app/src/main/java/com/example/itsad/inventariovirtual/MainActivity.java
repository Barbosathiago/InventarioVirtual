package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void criarInventario(View view) {
        Intent intent = new Intent(this, InventoryCreationActivity.class);
        startActivity(intent);
    }
    public void selecionarInventario(View view){
        Intent intent = new Intent(this, SelectInventoryActivity.class);
        startActivity(intent);
    }
}
