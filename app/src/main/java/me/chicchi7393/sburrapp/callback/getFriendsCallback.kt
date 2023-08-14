package me.chicchi7393.sburrapp.callback

import android.content.Context
import android.widget.Toast
import me.chicchi7393.sburrapp.helpers.Singleton
import me.chicchi7393.sburrapp.responses.GetFriendsRes
import retrofit2.Callback

fun getFriendsCallback(context: Context) = object : Callback<GetFriendsRes> {
    override fun onResponse(
        call: retrofit2.Call<GetFriendsRes>,
        response: retrofit2.Response<GetFriendsRes>
    ) {
        if (response.body()!!.reason == null) {
            Singleton.friends.value = response.body()!!.friends ?: listOf()
        } else {
            Toast.makeText(context, "Porcodio un errore: ${response.body()!!.reason}", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onFailure(call: retrofit2.Call<GetFriendsRes>, t: Throwable) {
        Toast.makeText(context, "Porcodio un errore", Toast.LENGTH_SHORT)
            .show()
    }

}