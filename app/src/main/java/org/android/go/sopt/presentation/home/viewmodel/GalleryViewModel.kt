package org.android.go.sopt.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.data.api.ServicePool.musicService
import org.android.go.sopt.data.remote.model.MusicDto
import org.android.go.sopt.util.ContentUriRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryViewModel: ViewModel() {
    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    private val _music = MutableLiveData<MusicDto>()
    val music: LiveData<MusicDto>
        get() = _music

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun uploadMusic(
        id: String,
        title: String,
        singer: String
    ) {
        if (image.value == null) { /* 아직 사진이 등록되지 않았다는 로직 처리 */
        } else {
            val titleData = title.toString().toRequestBody("text/plain".toMediaType())
            val singerData = singer.toString().toRequestBody("text/plain".toMediaType())
            val imageData = image.value!!.toFormData()
            musicService.uploadMusic(id, titleData, singerData, imageData).enqueue(
                object : Callback<MusicDto> {
                    override fun onResponse(call: Call<MusicDto>, response: Response<MusicDto>) {
                        if (response.isSuccessful){
                            _music.value = response.body()
                        } else {
                            Log.e("uploadMusic", "error")
                        }
                    }

                    override fun onFailure(call: Call<MusicDto>, t: Throwable) {
                        Log.e("uploadMusic", "error")
                    }

                }
            )
        }
    }
}