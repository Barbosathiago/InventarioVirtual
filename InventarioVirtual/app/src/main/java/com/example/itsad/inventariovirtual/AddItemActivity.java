package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.itsad.inventariovirtual.Helpers.PictureTaker;
import com.example.itsad.inventariovirtual.Models.Inventario;
import com.example.itsad.inventariovirtual.Models.Item;
import com.example.itsad.inventariovirtual.Repositorio.InventarioRepository;
import com.example.itsad.inventariovirtual.Repositorio.ItemRepository;

public class AddItemActivity extends PictureTaker {

    public static final String ISUPDATE="ISUPDATE";
    ImageView mImageView;
    EditText mEditText;
    Button mButton;
    String path;
    ItemRepository itemRepository;
    InventarioRepository inventarioRepository;
    Inventario mInventario;
    private Item _updateItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        inventarioRepository = new InventarioRepository(this);
        String id = getIntent().getStringExtra("_id");
        mInventario = inventarioRepository.getInventarioById(Long.parseLong(id));
        mImageView = (ImageView)findViewById(R.id.imageViewImagem);
        mEditText = (EditText)findViewById(R.id.editTextDescricao);
        mButton = (Button)findViewById(R.id.buttonAdd);


        itemRepository = new ItemRepository(this);
        mImageView.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View view) {
                path = takePicture();
            }
        });
        String tipo = getIntent().getStringExtra(ISUPDATE);
        if (getIntent().getStringExtra(ISUPDATE) != null )
        {
            if(getIntent().getStringExtra(ISUPDATE).equals("UPDATE")) {
                _updateItem = new Item();
                _updateItem = itemRepository.getItemById(Long.parseLong(getIntent().getStringExtra("_idItem")));
                mEditText.setText(_updateItem.getDescricao());
                if(_updateItem.getImagem() != null) {
                    mImageView.setImageBitmap(getPicture(_updateItem.getImagem()));
                }
                mButton.setText("SALVAR");
                mButton.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateItem();
                    }
                });
            }
        } else {
            mButton.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View view) {
                    saveItem();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            mImageView.setImageBitmap(getPicture(path));
        }
    }

    private void updateItem(){
        try {
            Item i = new Item();
            i.setDescricao(mEditText.getText().toString());
            if(path == null){
                i.setImagem(_updateItem.getImagem());
            }
            else {
                i.setImagem(path);
            }
            i.setInventario(mInventario);
            i.setId(Integer.parseInt(getIntent().getStringExtra("_idItem")));
            itemRepository.updateItem(i);
            ShowMessage("Item atualizado!");
        } catch(Exception e){
            ShowMessage(e.getMessage());
        } finally {
            finish();
        }

    }

    private void saveItem(){

        try{
            Item i = new Item();
            i.setDescricao(mEditText.getText().toString());
            i.setImagem(path);
            i.setInventario(mInventario);
            int what = itemRepository.insert(i);
            ShowMessage("Id item: " + what);
        } catch(Exception e){
            ShowMessage(e.getMessage());
        } finally {
            finish();
        }
    }
}
