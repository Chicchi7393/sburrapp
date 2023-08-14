package me.chicchi7393.sburrapp.firebase

import android.Manifest
import android.accounts.AccountManager
import android.app.Notification
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import me.chicchi7393.sburrapp.R
import me.chicchi7393.sburrapp.callback.changeFcmCallback
import me.chicchi7393.sburrapp.helpers.HoSburratoHTTP
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// un ringraziamento a prashant che arriva sempre in corner
class NotificheSbugmate: FirebaseMessagingService() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://sburrapi.chicchi7393.xyz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HoSburratoHTTP::class.java)

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        /*if (message.notification != null) {

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                if (ActivityCompat.checkSelfPermission(
                        this@NotificheSbugmate,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(
                    message.hashCode(),
                    NotificationCompat.Builder(this@NotificheSbugmate, "AGG_SBURRAT")
                        .setContentTitle(message.notification!!.title)
                        .setContentText(message.notification!!.body)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build()
                )
            }
        }*/

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID);

        retrofit.cambiaFcm(deviceId, token).enqueue(changeFcmCallback(this))
    }
}