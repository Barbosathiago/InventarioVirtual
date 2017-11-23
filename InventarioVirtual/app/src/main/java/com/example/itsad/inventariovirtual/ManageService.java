package com.example.itsad.inventariovirtual;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.itsad.inventariovirtual.Helpers.StateRecognizerActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import java.lang.annotation.Annotation;

/**
 * Created by itsad on 22/11/2017.
 */

public class ManageService extends Service implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;
    PendingIntent pendingIntent;

    private String idInventario;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        idInventario = intent.getStringExtra("_idInventario");
        if(intent.getStringExtra("TIPO").equals("FINALIZAR")){
            StopTrackingActivities();
            Intent restart = new Intent(this, ItemActivity.class);
            restart.putExtra("_id", idInventario);
            restart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(restart);

        }else if(intent.getStringExtra("TIPO").equals("INICIAR")){
            StartTrackingActivities();
            Intent i = new Intent(this, CheckItensActivity.class);
            i.putExtra("created", "true");
            i.putExtra("_idInventario", idInventario);
            startActivity(i);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    protected void StartTrackingActivities(){
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mApiClient.connect();
    }

    protected void StopTrackingActivities(){
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mApiClient,pendingIntent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(this, ActivityRecognizerService.class);
        intent.putExtra("_idInventario", idInventario);
        pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 1000, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
