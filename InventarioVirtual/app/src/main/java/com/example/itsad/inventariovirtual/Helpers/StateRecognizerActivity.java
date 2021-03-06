package com.example.itsad.inventariovirtual.Helpers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.itsad.inventariovirtual.ActivityRecognizerService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzbbz;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;

import static java.security.AccessController.getContext;

/**
 * Created by itsad on 21/11/2017.
 */

public class StateRecognizerActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void StartTrackingActivities(){
        mApiClient = new GoogleApiClient.Builder(StateRecognizerActivity.this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(StateRecognizerActivity.this)
                .addOnConnectionFailedListener(StateRecognizerActivity.this)
                .build();
        mApiClient.connect();

    }
    protected void StopTrackingActivities(){
        mApiClient = new GoogleApiClient.Builder(StateRecognizerActivity.this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(StateRecognizerActivity.this)
                .addOnConnectionFailedListener(StateRecognizerActivity.this)
                .build();
        mApiClient.connect();

        Intent intent = new Intent(StateRecognizerActivity.this, ActivityRecognizerService.class);
        intent.putExtra("_idInventario", getIntent().getStringExtra("_idInventario"));
        PendingIntent pendingIntent = PendingIntent.getService(StateRecognizerActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mApiClient, pendingIntent);
        mApiClient.disconnect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(StateRecognizerActivity.this, ActivityRecognizerService.class);
        intent.putExtra("_idInventario", getIntent().getStringExtra("_idInventario"));
        PendingIntent pendingIntent = PendingIntent.getService(StateRecognizerActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 1000, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
