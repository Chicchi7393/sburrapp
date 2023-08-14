package me.chicchi7393.sburrapp.callback

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.chicchi7393.sburrapp.helpers.DatastoreHelper
import me.chicchi7393.sburrapp.helpers.Singleton
import me.chicchi7393.sburrapp.responses.RegisterRes
import retrofit2.Callback

fun registerCallback(
    showModal: MutableState<Boolean>,
    coroutineScope: CoroutineScope,
    datastoreHelper: DatastoreHelper,
    context: Context,
) = object : Callback<RegisterRes> {
    override fun onResponse(
        call: retrofit2.Call<RegisterRes>,
        response: retrofit2.Response<RegisterRes>
    ) {
        if (response.body()!!.changed) {
            coroutineScope.launch {
                datastoreHelper.setDeviceId(response.body()!!.deviceId ?: "")
            }
            Singleton.deviceId = response.body()!!.deviceId ?: ""
            showModal.value = false
        } else {
            Toast.makeText(context, "Porcodio un errore: ${response.body()!!.reason}", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onFailure(call: retrofit2.Call<RegisterRes>, t: Throwable) {
        Toast.makeText(context, "Porcodio un errore", Toast.LENGTH_SHORT)
            .show()
    }

}