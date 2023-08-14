package me.chicchi7393.sburrapp.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.chicchi7393.sburrapp.callback.changeFcmCallback
import me.chicchi7393.sburrapp.helpers.DatastoreHelper
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


    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val datastoreHelper = DatastoreHelper(this)
        val deviceIdFlow = datastoreHelper.getDeviceId()

        GlobalScope.launch {
            deviceIdFlow.collect {
                if (it != null) {
                    retrofit.cambiaFcm(it, token).enqueue(changeFcmCallback(this@NotificheSbugmate))
                }
            }
        }
    }
}