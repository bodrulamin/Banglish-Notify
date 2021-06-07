package bodrulamin.bandbangla

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import java.io.ByteArrayOutputStream
import java.util.*

class NotificationsService : NotificationListenerService() {
    var context: Context? = null

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationBuilder: Notification.Builder
    private lateinit var reverseParser: ReverseParser;

    private val channelId = "deeksha"
    private val desc = "Notifications"


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        reverseParser = ReverseParser()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        if (sbn.notification.flags and Notification.FLAG_GROUP_SUMMARY !== 0) {
            //Ignore the notification
                sbn.notification.channelId
            return
        }
        val packageName = sbn.packageName
        var nTicker = ""
        if (sbn.notification.tickerText != null) {
            nTicker = sbn.notification.tickerText.toString()
        }

        val extras = sbn.notification.extras
        var title = extras.getString("android.title")
        val text = extras.getCharSequence("android.text").toString()
        val id1 = extras.getInt(Notification.EXTRA_SMALL_ICON)
        val id = sbn.notification.largeIcon
        Log.i("Package", packageName)
        Log.i("Ticker", nTicker)
        Log.i("Title", title!!)
        Log.i("Text", text)
        val msgInfo = Intent("Msg")
        msgInfo.putExtra("package", packageName)
        msgInfo.putExtra("nTicker", nTicker)
        msgInfo.putExtra("title", title)
        msgInfo.putExtra("text", text)
        if (id != null) {
            val stream = ByteArrayOutputStream()
            id.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            msgInfo.putExtra("icon", byteArray)
        }

        if (packageName == "com.whatsapp" ) {
            var ntitle = reverseParser.parse(title)
            var ntext = reverseParser.parse(text)
            notifyThis(ntitle, ntext)
        }

  //     if (packageName != context?.packageName ) {
//            var ntitle = reverseParser.parse(title)
//            var ntext = reverseParser.parse(text)
//            notifyThis(ntitle, ntext)
//        }


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)


    }


    fun notifyThis(title: String?, message: String?) {


        Log.i("TAG", "onCreate: fdfs")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, desc, NotificationManager.IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setChannelId(channelId)
                .setLargeIcon(BitmapFactory.decodeResource(context?.resources, R.drawable.ic_baseline_notifications_24))
        } else {

            notificationBuilder = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setLargeIcon(BitmapFactory.decodeResource(context?.resources, R.drawable.ic_baseline_notifications_24))
        }

        val rand = Random()
        val notId: Int = rand.nextInt(1000)
        notificationManager.notify(notId, notificationBuilder.build())

    }


}