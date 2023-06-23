package org.android.go.sopt.data.remote.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.data.remote.model.MusicDto
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MusicService {
    @Multipart
    @POST("music")
    fun uploadMusic(
        @Header("id") id: String,
        @Part("title") title: RequestBody,
        @Part("singer") singer: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<MusicDto>
}