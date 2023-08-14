package me.chicchi7393.sburrapp.helpers

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import me.chicchi7393.sburrapp.responses.model.Friend

object Singleton {
    val friends = mutableStateOf(
        listOf<Friend>()
    )
}