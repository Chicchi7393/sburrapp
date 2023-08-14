package me.chicchi7393.sburrapp.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import me.chicchi7393.sburrapp.responses.model.Friend

object Singleton {
    val friends = mutableStateOf(
        listOf<Friend>()
    )
    var ack by mutableStateOf(false)
    var deviceId: String? by mutableStateOf(null)

}