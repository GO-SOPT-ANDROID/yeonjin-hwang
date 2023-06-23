package org.android.go.sopt.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.android.go.sopt.data.remote.service.*
import org.android.go.sopt.util.Server.ServerURL
import retrofit2.Retrofit
import retrofit2.create

object ApiFactory {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ServerURL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val retrofit1: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
    inline fun <reified T> create1(): T = retrofit1.create<T>(T::class.java)
}

object ServicePool {
    val signUpService = ApiFactory.create<SignUpService>()
    val loginService = ApiFactory.create<LoginService>()
    val followerService = ApiFactory.create1<FollowerService>()
    val imageService = ApiFactory.create<ImageService>()
    val musicService = ApiFactory.create<MusicService>()
}