package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.model.ResponseFollowerDto
import retrofit2.Call
import retrofit2.http.GET

interface FollowerService {
    @GET("users?page=2")
    fun loadFollower(): Call<ResponseFollowerDto>
}