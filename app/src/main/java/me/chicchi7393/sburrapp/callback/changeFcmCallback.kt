package me.chicchi7393.sburrapp.callback

import android.content.Context
import android.widget.Toast
import me.chicchi7393.sburrapp.responses.RegisterRes
import retrofit2.Callback

fun changeFcmCallback(context: Context) = object : Callback<RegisterRes> {
    override fun onResponse(
        call: retrofit2.Call<RegisterRes>,
        response: retrofit2.Response<RegisterRes>
    ) {
        if (!response.body()!!.changed) {
            Toast.makeText(context, "Porcodio un errore: ${response.body()!!.reason}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onFailure(call: retrofit2.Call<RegisterRes>, t: Throwable) {
        Toast.makeText(context, "Porcodio un errore", Toast.LENGTH_SHORT)
            .show()
    }

}