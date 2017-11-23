package com.example.itsad.inventariovirtual;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.example.itsad.inventariovirtual.Helpers.StateRecognizerActivity;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by Thiago Barbosa  on 21/11/2017.
 */

public class ActivityRecognizerService extends IntentService{

    public ActivityRecognizerService(){ super("ActivityRecognizerService"); }
    public ActivityRecognizerService(String name){ super(name); }
    private String id = "";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        id = intent.getStringExtra("_idInventario");
        if(ActivityRecognitionResult.hasResult(intent)){
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivity(result.getProbableActivities());
        }

    }

    private void ShowForgottenNotification(DetectedActivity activity){
        if (activity.getConfidence() >= 50) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentText("Não está se esquecendo de nada?");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Cheque seus itens!");
            builder.setAutoCancel(true);
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
            builder.setLights(Color.RED, 3000, 3000);

            Intent resultIntent = new Intent(this, CheckItensActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.putExtra("_idInventario", id);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(CheckItensActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, builder.build());


        }
    }

    private void TesteNotification(){
        Intent intent = new Intent(this, CheckItensActivity.class);
        intent.putExtra("_idInventario", id);
        startActivity(intent);
    }

    private void handleDetectedActivity(List<DetectedActivity> probableActivities){
        for(DetectedActivity activity: probableActivities){
            switch (activity.getType()){
                case DetectedActivity.IN_VEHICLE:{
                    ShowForgottenNotification(activity);
                    break;
                }
                case DetectedActivity.ON_BICYCLE:{
                    ShowForgottenNotification(activity);
                    break;
                }
                case DetectedActivity.ON_FOOT:{
                    ShowForgottenNotification(activity);
                    break;
                }
                case DetectedActivity.RUNNING:{
                    ShowForgottenNotification(activity);
                    break;
                }
                case DetectedActivity.STILL:{
                    ShowForgottenNotification(activity);
                    //TesteNotification();
                    break;
                }
                case DetectedActivity.TILTING:{
                    ShowForgottenNotification(activity);
                    break;
                }
                case DetectedActivity.UNKNOWN:{
                    ShowForgottenNotification(activity);
                    break;
                }
                case DetectedActivity.WALKING:{
                    ShowForgottenNotification(activity);
                    break;
                }
                default:{
                    break;
                }

            }
        }
    }
}
