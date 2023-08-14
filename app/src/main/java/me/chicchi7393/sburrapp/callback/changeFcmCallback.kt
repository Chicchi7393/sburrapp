package me.chicchi7393.sburrapp.callback

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.chicchi7393.sburrapp.helpers.DatastoreHelper
import me.chicchi7393.sburrapp.responses.ChangeUsernameRes
import me.chicchi7393.sburrapp.responses.HoSburratoRes
import retrofit2.Callback

fun changeFcmCallback(context: Context) = object : Callback<ChangeUsernameRes> {
    override fun onResponse(
        call: retrofit2.Call<ChangeUsernameRes>,
        response: retrofit2.Response<ChangeUsernameRes>
    ) {
        if (!response.body()!!.changed) {
            Toast.makeText(context, "Porcodio un errore: ${response.body()!!.reason}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onFailure(call: retrofit2.Call<ChangeUsernameRes>, t: Throwable) {
        Toast.makeText(context, "Porcodio un errore", Toast.LENGTH_SHORT)
            .show()
    }

}