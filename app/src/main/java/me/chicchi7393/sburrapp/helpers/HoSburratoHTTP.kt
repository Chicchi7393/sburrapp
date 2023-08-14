package me.chicchi7393.sburrapp.helpers

import me.chicchi7393.sburrapp.responses.RegisterRes
import me.chicchi7393.sburrapp.responses.GenericRes
import me.chicchi7393.sburrapp.responses.GetFriendsRes
import me.chicchi7393.sburrapp.responses.HoSburratoRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface HoSburratoHTTP {
    @POST("register")
    fun registra(@Query("username") username: String, @Query("password") password: String): Call<RegisterRes>

    @POST("changeFcm")
    fun cambiaFcm(@Header("SBU-DeviceId") deviceId: String, @Query("fcm") fcm: String): Call<RegisterRes>

    @POST("isburred")
    fun hoSburratoReq(@Header("SBU-DeviceId") deviceId: String, @Query("con") consistency: String, @Query("hon") honour: String, @Query("where") where: String): Call<HoSburratoRes>

    @GET("getFriends")
    fun getFriends(@Header("SBU-DeviceId") deviceId: String): Call<GetFriendsRes>

    @POST("addFriend")
    fun addFriend(@Header("SBU-DeviceId") deviceId: String, @Query("friendCode") friendCode: String): Call<GenericRes>

    @POST("deleteFriend")
    fun deleteFriend(@Header("SBU-DeviceId") deviceId: String, @Query("username") username: String): Call<GenericRes>
}