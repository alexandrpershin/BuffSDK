package com.buffup.sdk.di

import com.buffup.sdk.api.repository.BuffRepository
import com.buffup.sdk.api.response.BuffResponse
import com.buffup.sdk.model.BuffModel
import com.buffup.sdk.model.toModel
import io.mockk.mockk
import org.koin.dsl.module

/**
 * Koin module common to all unit tests.
 */
val testModule = module {
    single { mockk<BuffRepository>(relaxed = true) }
}
