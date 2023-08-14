package me.chicchi7393.sburrapp.responses

data class ChangeUsernameRes(
    val changed: Boolean,
    val reason: String?
)