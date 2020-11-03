package com.buffup.sdk.api.repository.repositoryImpl

import com.buffup.sdk.api.TaskResult
import com.buffup.sdk.api.executeAsyncRequest
import com.buffup.sdk.api.repository.BuffRepository
import com.buffup.sdk.api.response.BuffResponse
import com.buffup.sdk.api.service.BuffService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


/**
 * Concrete implementation of [BuffRepository] for providing data to ViewModel
 * */

  class BuffRepositoryImpl(
    private val buffService: BuffService,
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) : BuffRepository {

    override suspend fun getNextBuff(buffId: Int): TaskResult<BuffResponse> =
        withContext(coroutineContext) {
            return@withContext executeAsyncRequest(buffService.getBuff(buffId))
        }

}