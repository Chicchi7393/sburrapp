package me.chicchi7393.sburrapp.callback

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import me.chicchi7393.sburrapp.responses.GenericRes
import retrofit2.Callback

fun addFriendsCallback(
    context: Context,
    errorOpacity: MutableState<Boolean>,
    errorText: MutableState<String>,
    selectedItem: MutableState<Int>
) = object : Callback<GenericRes> {
    override fun onResponse(
        call: retrofit2.Call<GenericRes>,
        response: retrofit2.Response<GenericRes>
    ) {
        if (response.body()!!.done) {
            errorOpacity.value = false
            errorText.value = ""
            selectedItem.value = 0
        } else {
            errorOpacity.value = true
            errorText.value = response.body()!!.reason ?: ""
        }

    }

    override fun onFailure(call: retrofit2.Call<GenericRes>, t: Throwable) {
        Toast.makeText(context, "Porcodio un errore", Toast.LENGTH_SHORT)
            .show()
    }

}