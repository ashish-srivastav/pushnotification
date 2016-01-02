package com.mta.pushnotification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    public GCMNotificationIntentService() {
        super("GCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();
        GoogleCloudMessaging cloudMessaging = GoogleCloudMessaging.getInstance(this);
        String msgType = cloudMessaging.getMessageType(intent);

        if (!bundle.isEmpty()){
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(msgType)){
                sendNotification("MESSAGE_TYPE_SEND_ERROR" + bundle.toString());

            }else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(msgType)){
                sendNotification("MESSAGE_TYPE_DELETED" + bundle.toString());

            }else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(msgType)){
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }

                }
                sendNotification(bundle.get(Config.MESSAGE_ID).toString());
            }
        }


    }

    private void sendNotification(String msg){

        RegistrationActivity.getInstance().addChat("rec",msg);
/*        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("GCM Notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg).setDefaults(Notification.DEFAULT_LIGHTS);

        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());*/
    }
}
