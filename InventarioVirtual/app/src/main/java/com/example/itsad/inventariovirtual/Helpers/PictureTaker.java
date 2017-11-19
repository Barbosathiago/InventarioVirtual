package com.example.itsad.inventariovirtual.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Thiago Barbosa on 16/11/2017.
 */

public class PictureTaker extends Activity {

    protected static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    protected static final int REQUEST_TAKE_PHOTO = 1;


    // Método que retorna a imagem através do caminho da mesma
    public Bitmap getPicture(String path){
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        } else
            return null;

    }
    // Método responsável por abrir a camera e guardar a imagem em um local específico
    // Retorna o caminho absoluto de armazenamento da imagem
    public String takePicture(){
        String path = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            } catch (IOException ex){
                ShowMessage(ex.getMessage());
                path = null;
            }
            if(photoFile != null){
                try{
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.itsad.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    path = mCurrentPhotoPath;
                } catch (Exception e){
                    ShowMessage(e.getMessage());
                    path =  null;
                }
            }
        }else{
            path = null;
        }
        return path;
    }

    private File createImageFile() throws IOException{
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timestamp+"_";
        String value = Environment.DIRECTORY_PICTURES;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    protected void ShowMessage(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}
