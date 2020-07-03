package com.example.restaurantapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.restaurantapp.Base
import com.example.restaurantapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val CHANNEL_ID = "FirebaseNotification Kotlin"
    val TAG = "Service"
    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage!!.from)
        Log.d(TAG, "Notification Message Body: " + remoteMessage.notification!!.body!!)
       if (remoteMessage.data !=null)
        sendNotification(remoteMessage)
//        val intent = Intent(this@MyFirebaseMessagingService, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.putExtra("message", remoteMessage.notification!!.body!!)
//        startActivity(intent)
    }
    //
//    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//
//    }
    private fun sendNotification(remoteMessage: RemoteMessage) {

        val title=remoteMessage.data["title"] //custom notification
        val content = remoteMessage.data["content"]
      /*  val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID= "Test"
        @RequiresApi(Build.VERSION_CODES.O)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            val notificationChannel =NotificationChannel(NOTIFICATION_CHANNEL_ID,"Test",NotificationManager.IMPORTANCE_MAX)
            notificationChannel.description="this is a test"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = (Color.RED)
            notificationChannel.vibrationPattern = longArrayOf(0,1000,500,500)
            notificationChannel.enableVibration(true)

            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.icons_login)
            .setContentTitle(title)
            .setContentText(content)

        notificationManager.notify(1,notificationBuilder.build())*/

        val intent = Intent(this, Base::class.java)
        intent.putExtra("notify","abc");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0  /*Request code*/ , intent,
            PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        createNotificationChannel()
        val aBigBitmap = BitmapFactory.decodeResource(this.resources, R.drawable.call_us_src)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText(remoteMessage.notification!!.body)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.icons_login)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(uri)
            .setLargeIcon(aBigBitmap)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(aBigBitmap))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0  /*ID of notification*/ , notificationBuilder.build())
    }

     private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d("p0", "From: " + p0)
    }
}
