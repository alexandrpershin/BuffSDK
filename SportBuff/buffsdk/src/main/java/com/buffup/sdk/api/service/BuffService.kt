package com.buffup.sdk.api.service

import com.buffup.sdk.api.response.BuffResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Api service for network level
 * */

  interface BuffService {

    @GET("buffs/{$KEY_BUFF_ID}")
    fun getBuff(@Path(KEY_BUFF_ID) id: Int): Deferred<Response<BuffResponse>>

    companion object {
        private const val KEY_BUFF_ID = "id"
    }
}
