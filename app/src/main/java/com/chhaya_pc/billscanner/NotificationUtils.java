package com.chhaya_pc.billscanner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

/**
 * Created by Daniel-pc on 3/13/2018.
 */

public class NotificationUtils extends ContextWrapper {
    public static final String ANDROID_CHANNEL_ID = "com.example.chhaya_pc.billscanner";
    public static final String IOS_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.IOS";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    private NotificationManager mManager;
    public static final String IOS_CHANNEL_NAME = "IOS CHANNEL";

        public NotificationUtils(Context base) {
        super(base);
        createChannels();
        }

        public void createChannels() {
            // create android channel
            NotificationChannel androidChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                        ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                // Sets whether notifications posted to this channel should display notification lights
                androidChannel.enableLights(true);
// Sets whether notification posted to this channel should vibrate.
                androidChannel.enableVibration(true);
// Sets the notification light color for notifications posted to this channel
                androidChannel.setLightColor(Color.GREEN);
// Sets whether notifications posted to this channel appear on the lockscreen or not
                androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                mManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                mManager.createNotificationChannel(androidChannel);
            }


        }
}
