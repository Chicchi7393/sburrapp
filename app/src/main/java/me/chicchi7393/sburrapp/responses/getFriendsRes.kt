package me.chicchi7393.sburrapp.responses

import me.chicchi7393.sburrapp.responses.model.Friend

data class GetFriendsRes(
    val friends: List<Friend>?,
    val reason: String?
)