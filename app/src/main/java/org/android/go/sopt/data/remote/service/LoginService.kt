package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.model.RequestLoginDto
import org.android.go.sopt.data.remote.model.ResponseLoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("sign-in")
    fun login(
        @Body request: RequestLoginDto
    ): Call<ResponseLoginDto>
}