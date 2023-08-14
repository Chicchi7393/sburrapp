package me.chicchi7393.sburrapp.responses

data class RegisterRes(
    val changed: Boolean,
    val reason: String?,
    val deviceId: String?
)