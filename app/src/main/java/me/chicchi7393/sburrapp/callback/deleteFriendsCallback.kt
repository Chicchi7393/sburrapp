package me.chicchi7393.sburrapp.callback

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import me.chicchi7393.sburrapp.responses.GenericRes
import retrofit2.Callback

fun deleteFriendsCallback(context: Context, selectedItem: MutableState<Int>) = object : Callback<GenericRes> {
    override fun onResponse(
        call: retrofit2.Call<GenericRes>,
        response: retrofit2.Response<GenericRes>
    ) {
        if (!response.body()!!.done) {
            Toast.makeText(context, "Porcodio un errore: ${response.body()!!.reason}", Toast.LENGTH_SHORT)
                .show()
        } else {
            selectedItem.value = 0
        }

    }

    override fun onFailure(call: retrofit2.Call<GenericRes>, t: Throwable) {
        Toast.makeText(context, "Porcodio un errore", Toast.LENGTH_SHORT)
            .show()
    }

}