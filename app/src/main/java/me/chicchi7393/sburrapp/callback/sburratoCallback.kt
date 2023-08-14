package me.chicchi7393.sburrapp.callback

import android.content.Context
import android.widget.Toast
import me.chicchi7393.sburrapp.responses.HoSburratoRes
import retrofit2.Callback

fun sburratoCallback(context: Context) = object : Callback<HoSburratoRes> {
    override fun onResponse(
        call: retrofit2.Call<HoSburratoRes>,
        response: retrofit2.Response<HoSburratoRes>
    ) {
        if (response.body()!!.sburraSent) {
            Toast.makeText(context, "Hai effettivamente sburrat", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, "Porcodio un errore: ${response.body()!!.reason}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onFailure(call: retrofit2.Call<HoSburratoRes>, t: Throwable) {
        Toast.makeText(context, "Porcodio un errore", Toast.LENGTH_SHORT).show()
    }

}