package com.example.govimithuruapp.core;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.govimithuruapp.R;
import com.example.govimithuruapp.accountManagement.WelcomeActivity;

public class NotificationController {
    private Context context;

    private static final String CHANNEL_ID = "com.example.govimithuruapp.notificationChannel";
    private static final int NOTIFICATION_ID = 1000;

    private boolean created;

    private static NotificationController instance;

    private NotificationController(Context context) {
        createNotificationChannel(context);
        created = false;
    }

    public static NotificationController getInstance(Context context) {
        if (instance == null) instance = new NotificationController(context);
        instance.context = context;
        return instance;
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "GoviMithuNotificationChannel";
            String description = "GoviMithuNotificationChannel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification() {
        if (!created) {
            Intent intent = new Intent(context, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentText(context.getResources().getString(R.string.txt_notification))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Prevent user from dismissing, stays forever if the user kills the app
                    .setOngoing(true)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(NOTIFICATION_ID, builder.build());
            created = true;
        }
    }

    public void removeNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFICATION_ID);
        created = false;
    }
}
