package me.chicchi7393.sburrapp.ui.components

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.chicchi7393.sburrapp.helpers.HoSburratoHTTP
import me.chicchi7393.sburrapp.ui.components.pages.Friends
import me.chicchi7393.sburrapp.ui.components.pages.Homepage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("HardwareIds")
@Composable
fun MainContent(selectedItem: MutableState<Int>, paddingScaffold: PaddingValues) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://sburrapi.chicchi7393.xyz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HoSburratoHTTP::class.java)

    val deviceId = Settings.Secure.getString(LocalContext.current.contentResolver, Settings.Secure.ANDROID_ID);

    Box(modifier = Modifier
        .padding(paddingScaffold)
        .fillMaxSize()) {
        when (selectedItem.value) {
            0 -> Homepage(retrofit, deviceId)
            1 -> Friends(retrofit, deviceId, selectedItem)
        }
    }
}
