package bodrulamin.bandbangla


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationBuilder: Notification.Builder

    private val channelId = "deeksha"
    private val desc = "Notifications"
    private val TAG = "BandBangla"

    fun installedApps() {
        val packList = packageManager.getInstalledPackages(0)
        for (i in packList.indices) {
            val packInfo = packList[i]  
            if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appName = packInfo.applicationInfo.loadLabel(packageManager).toString()
                Log.e("App № " + Integer.toString(i), appName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val aBtn = findViewById<Button>(R.id.applistBtn)
        aBtn.setOnClickListener {
          //  installedApps()


        }



        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val btn = findViewById<Button>(R.id.btn)
        val accBtn = findViewById<Button>(R.id.accBtn)
        accBtn.setOnClickListener {
            getApplicationContext().startActivity(
                Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            );

        }


        btn.setOnClickListener {
          //  notifyThis("TestTitle","TestMessage")
        }

    }


/*
    fun notifyThis(title: String?, message: String?) {
        Log.i("TAG", "onCreate: fdfs")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, desc, NotificationManager.IMPORTANCE_HIGH)


            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lightColor = Color.GRAY

            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder = Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setChannelId(channelId)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_baseline_notifications_24))
        } else {

            notificationBuilder = Notification.Builder(this)
                .setContentTitle("likghlh")
                .setContentText("kuhkhkjn")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)

                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_baseline_notifications_24))

        }


        notificationManager.notify(234, notificationBuilder.build())
    }
*/





}

