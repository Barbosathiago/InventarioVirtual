package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.itsad.inventariovirtual.Helpers.StateRecognizerActivity;
import com.example.itsad.inventariovirtual.Models.Item;
import com.example.itsad.inventariovirtual.Repositorio.InventarioRepository;
import com.example.itsad.inventariovirtual.Repositorio.ItemRepository;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.nearby.messages.internal.Update;

import java.util.ArrayList;
import java.util.List;

public class CheckItensActivity extends Activity {

    private InventarioRepository mInventarioRepository;
    private ItemRepository mItemRepository;
    private String inventarioId;
    ListView mListView;
    Cursor itensCursor;
    List<Item> itens;
    Button btnFinalizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_itens);
        mListView = (ListView) findViewById(R.id.checkListItens);
        btnFinalizar = (Button) findViewById(R.id.btnTerminarOcorrencia);
        inventarioId = getIntent().getStringExtra("_idInventario");
        mItemRepository = new ItemRepository(this);
        mItemRepository.setItensPresentStateByInventory(Long.parseLong(getIntent().getStringExtra("_idInventario")), Item.FALSE);
        btnFinalizar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckItensActivity.this, ManageService.class);
                i.putExtra("TIPO", "FINALIZAR");
                i.putExtra("_idInventario", inventarioId);
                startService(i);
            }
        });

        String value = getIntent().getStringExtra("created");
        if(value != null){
            if(getIntent().getStringExtra("created").equals("true")){


                UpdateList();

                if (!checaVerificados()) {
                    btnFinalizar.setEnabled(false);
                }
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mItemRepository.toggleItemPresentState(l);
                        itensCursor = mItemRepository.getItensByInventario(Long.parseLong(inventarioId));
                        if (checaVerificados()) {
                            btnFinalizar.setEnabled(true);
                        } else {
                            btnFinalizar.setEnabled(false);
                        }
                    }
                });
            }
        }
        else {
            UpdateList();
            if (!checaVerificados()) {
                btnFinalizar.setEnabled(false);
            }
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mItemRepository.toggleItemPresentState(l);
                    itensCursor = mItemRepository.getItensByInventario(Long.parseLong(inventarioId));
                    if (checaVerificados()) {
                        btnFinalizar.setEnabled(true);
                    } else {
                        btnFinalizar.setEnabled(false);
                    }
                }
            });
        }
    }

    private void UpdateList(){
        itensCursor = mItemRepository.getItensByInventario(Long.parseLong(inventarioId));
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_checked, itensCursor, new String[]{"descricao"}, new int[]{android.R.id.text1},0);
        simpleCursorAdapter.notifyDataSetChanged();
        mListView.setAdapter(simpleCursorAdapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private boolean checaVerificados(){
        itens = new ArrayList<Item>();
        boolean allPresent = true;
        for(itensCursor.moveToFirst(); !itensCursor.isAfterLast(); itensCursor.moveToNext()){
            Item i = new Item();
            i.setDescricao(itensCursor.getString(itensCursor.getColumnIndex("descricao")));
            i.setImagem(itensCursor.getString(itensCursor.getColumnIndex("imagem")));
            i.setId(itensCursor.getInt(itensCursor.getColumnIndex("_id")));
            i.setPresente(itensCursor.getInt(itensCursor.getColumnIndex("presente")));
            itens.add(i);
        }
        for (Item i: itens){
            if (i.getPresente() == Item.FALSE){
                allPresent = false;
            }
        }
        return  allPresent;
    }
}
