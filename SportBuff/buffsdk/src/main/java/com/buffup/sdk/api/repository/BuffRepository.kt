package com.buffup.sdk.api.repository

import com.buffup.sdk.api.TaskResult
import com.buffup.sdk.api.response.BuffResponse


/**
 * Repository interface for providing data to ViewModel
 * */

interface BuffRepository {
    suspend fun getNextBuff(buffId: Int): TaskResult<BuffResponse>
}